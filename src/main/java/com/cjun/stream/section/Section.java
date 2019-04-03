package com.cjun.stream.section;

public class Section {
	private SectionHeader header ;
	private byte[] body;
	
	protected Section(){};
	
	public Section(byte[] data) {
		// TODO Auto-generated constructor stub
		header = new SectionHeader(data);
		body = new byte[data.length-8];
		System.arraycopy(data, 8, body, 0, data.length-8);
	}

	public SectionHeader getHeader() {
		return header;
	}

	public byte[] getBody() {
		return body;
	}
	
	
}
