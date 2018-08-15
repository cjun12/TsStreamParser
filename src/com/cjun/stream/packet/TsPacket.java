package com.cjun.stream.packet;

public class TsPacket {
	private TsPacketHeader header;
	private int length;
	private byte[] body;

	public TsPacket(byte[] data, int length) {
		// TODO Auto-generated constructor stub
		header = new TsPacketHeader(data);
		this.length = length;
		int skipHeaderLength = TsPacketHeader.HEADER_LENGTH;
		if (header.getPayload_unit_start_indicator() == 0x01) {
			skipHeaderLength += 1;
		}
		int bodyLength  = length-skipHeaderLength;
		body = new byte[bodyLength];
		System.arraycopy(data, skipHeaderLength, body, 0, bodyLength);
	}

	public TsPacketHeader getHeader() {
		return header;
	}

	public byte[] getBody() {
		return body;
	}

	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "TsPacket [header=" + header + "]";
	}
}
