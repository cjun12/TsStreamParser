package com.cjun.stream.section;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cjun.stream.packet.TsPacket;
import com.cjun.stream.table.AbstractTable;
import com.cjun.stream.table.TableFactory;

public class SectionAssembler {

	private ByteArrayOutputStream out;
	private int length;
	private int sumBytes;
	private List<AbstractTable> tables = new ArrayList<AbstractTable>();

	public void accept(TsPacket packet) {
		try {
			if (packet.getHeader().getPayload_unit_start_indicator() == 0x01) {
				SectionHeader header = new SectionHeader(packet.getBody());
				length = header.getSection_length();
				if (out == null) {
					out = new ByteArrayOutputStream(1024);
					sumBytes = 0;
				}
				out.reset();
				out.write(packet.getBody());
			} else {
				out.write(packet.getBody());
				sumBytes += packet.getLength();
			}
			if (sumBytes >= length) {
				assemble();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void assemble() {
		byte[] bytes = out.toByteArray();
		Section section = new Section(bytes);
		tables.add(TableFactory.createTable(section));
	}

	public void clear() {
		if (tables != null) {
			tables.clear();
		}
	}
}
