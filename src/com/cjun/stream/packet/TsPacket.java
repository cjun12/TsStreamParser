package com.cjun.stream.packet;

import java.util.Arrays;

public class TsPacket {
	private TsPacketHeader header;
	private byte[] body;
	
	public TsPacket(byte[] data) {
		// TODO Auto-generated constructor stub
		header = new TsPacketHeader(data);
		body = new byte[data.length-4];
		System.arraycopy(data, 4, body, 0, data.length-4);
	}

	public TsPacketHeader getHeader() {
		return header;
	}

	public byte[] getBody() {
		return body;
	}
}
