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

	public void start()  {
		try {
			while (this.source.read() != TsPacketHeader.SYNC_BYTE)
				firstPacketPos++;
			this.source.reset();
			this.source.skip(firstPacketPos);
			int abyte;
			int pos = 0;
			while ((abyte = this.source.read()) != -1) {
				if (abyte == TsPacketHeader.SYNC_BYTE) {
					if (buf[0] == TsPacketHeader.SYNC_BYTE) {
						queue.offer(new TsPacket(buf),30, TimeUnit.SECONDS);
					}
					buf = new byte[204];
					pos = 0;
				}
				if (pos >= buf.length)
					return;
				buf[pos++] = (byte) abyte;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TsPacket getTsPacket() throws InterruptedException{
		return queue.poll(30, TimeUnit.SECONDS);
	}
}
