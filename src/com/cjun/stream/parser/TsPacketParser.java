package com.cjun.stream.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.cjun.stream.packet.TsPacket;
import com.cjun.stream.packet.TsPacketHeader;

public class TsPacketParser {
	private static final int BASE_TS_PACKET_LENGTH = 188;
	private int firstPacketPos;
	private LinkedBlockingQueue<TsPacket> queue = new LinkedBlockingQueue<>(16);
	private byte[] buf;
	public InputStream source;

	public TsPacketParser(InputStream source) {
		// TODO Auto-generated constructor stub
		this.source = source;
		firstPacketPos = 0;
	}

	public void start() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					TsPacketParser.this.source.mark(0);
					int temp;
					while ((temp = TsPacketParser.this.source.read()) != TsPacketHeader.SYNC_BYTE) {
						System.out.println(temp);
						firstPacketPos++;
					}
					TsPacketParser.this.source.reset();
					TsPacketParser.this.source.skip(firstPacketPos);
					int aByte;
					int pos = 0;
					while ((aByte = TsPacketParser.this.source.read()) != -1) {
						if (aByte == TsPacketHeader.SYNC_BYTE && (pos >= 188 || pos == 0)) {
							if (buf != null && buf[0] == TsPacketHeader.SYNC_BYTE) {
								queue.offer(new TsPacket(buf, pos), 30, TimeUnit.SECONDS);
							}
							buf = new byte[204];
							pos = 0;
						}
						if (pos >= buf.length)
							return;
						buf[pos++] = (byte) (aByte & 0xff);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.start();

	}

	public TsPacket getTsPacket() throws InterruptedException {
		return queue.poll(30, TimeUnit.SECONDS);
	}
}
