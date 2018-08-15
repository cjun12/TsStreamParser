package com.cjun.stream.packet;

import java.util.List;

public class TsPacketHeader {
	public final static int HEADER_LENGTH = 4;
	public final static int SYNC_BYTE = 0x47;

	private int sync_byte;
	private int transport_error_indicator;
	private int payload_unit_start_indicator;
	private int transport_priority;
	private int pid;
	private int transport_scrambling_control;
	private int adaptation_field_control;
	private int continuity_counter;

	/*
	 * 0 7 8 9 10 23 25 27 31 |------|--|--|--|------------|------|-----|-----|
	 */

	/*
	 * << >> & ^ |
	 */
	public TsPacketHeader(byte[] headerBytes) {
		// TODO Auto-generated constructor stub
		if (headerBytes.length < HEADER_LENGTH)
			throw new IllegalArgumentException("The header of TS packet has 4 bytes");
		this.sync_byte = headerBytes[0];
		this.transport_error_indicator = headerBytes[1] >> 7 & 0x01;
		this.payload_unit_start_indicator = headerBytes[1] >> 6 & 0x01;
		this.transport_priority = headerBytes[1] >> 5 & 0x01;
		this.pid = (headerBytes[1] << 8 | headerBytes[2]&0xff) & 0x1fff;
		this.transport_scrambling_control = headerBytes[3] >> 6 &0x03;
		this.adaptation_field_control = headerBytes[3] >> 4 & 0x03;
		this.continuity_counter = headerBytes[3] & 0x0f;
	}
	
	public TsPacketHeader(List<Byte> headerBytes) {
		// TODO Auto-generated constructor stub
		if (headerBytes.size() < HEADER_LENGTH)
			throw new IllegalArgumentException("The header of TS packet has 4 bytes");
		this.sync_byte = headerBytes.get(0);
		this.transport_error_indicator = headerBytes.get(1) >> 7;
		this.payload_unit_start_indicator = headerBytes.get(1) >> 6 & 0x01;
		this.transport_priority = headerBytes.get(1) >> 5 & 0x01;
		this.pid = (headerBytes.get(1) << 8 | headerBytes.get(2)) & 0x1fff;
		this.transport_scrambling_control = headerBytes.get(3) >> 6;
		this.adaptation_field_control = headerBytes.get(3) >> 4 & 0x03;
		this.continuity_counter = headerBytes.get(3) & 0x0f;
	}

	public int getSync_byte() {
		return sync_byte;
	}

	public int getTransport_error_indicator() {
		return transport_error_indicator;
	}

	public int getPayload_unit_start_indicator() {
		return payload_unit_start_indicator;
	}

	public int getTransport_priority() {
		return transport_priority;
	}

	public int getPid() {
		return pid;
	}

	public int getTransport_scrambling_control() {
		return transport_scrambling_control;
	}

	public int getAdaptation_field_control() {
		return adaptation_field_control;
	}

	public int getContinuity_counter() {
		return continuity_counter;
	}

	@Override
	public String toString() {
		return "TsPacketHeader [sync_byte=" + sync_byte + ", transport_error_indicator=" + transport_error_indicator
				+ ", payload_unit_start_indicator=" + payload_unit_start_indicator + ", transport_priority="
				+ transport_priority + ", pid=" + pid + ", transport_scrambling_control=" + transport_scrambling_control
				+ ", adaptation_field_control=" + adaptation_field_control + ", continuity_counter="
				+ continuity_counter + "]";
	}
	

}
