package com.cjun.stream.section;

public class SectionHeader {
	private int table_id;
	private int section_syntax_indicator;
	private int zero;
	private int firstReserved;
	private int section_length;
	private int transport_stream_id;
	private int secondReserved;
	private int version_number;
	private int current_next_indicator;
	private int section_number;
	private int last_section_number;

	public SectionHeader(byte[] data) {
		this.table_id = data[0];
		this.section_syntax_indicator = data[1] >> 7 & 0x01;
		this.zero = data[1] >> 6 & 0x01;
		this.firstReserved = data[1] >> 4 & 0x03;
		this.section_length = (data[1] << 8 | data[2] & 0xff) & 0x0fff;
		this.transport_stream_id = data[3] << 8 | data[4] & 0xff;
		this.secondReserved = data[5] >> 6 & 0x03;
		this.version_number = data[5] >> 1 & 0x1f;
		this.current_next_indicator = data[5] & 0x01;
		this.section_number = data[6] & 0xff;
		this.last_section_number = data[7] & 0xff;
	}

	public int getTable_id() {
		return table_id;
	}

	public int getSection_syntax_indicator() {
		return section_syntax_indicator;
	}

	public int getZero() {
		return zero;
	}

	public int getFirstReserved() {
		return firstReserved;
	}

	public int getSection_length() {
		return section_length;
	}

	public int getTransport_stream_id() {
		return transport_stream_id;
	}

	public int getSecondReserved() {
		return secondReserved;
	}

	public int getVersion_number() {
		return version_number;
	}

	public int getCurrent_next_indicator() {
		return current_next_indicator;
	}

	public int getSection_number() {
		return section_number;
	}

	public int getLast_section_number() {
		return last_section_number;
	}

}
