package com.cjun.stream.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cjun.stream.packet.TsPacket;
import com.cjun.stream.section.Section;
import com.cjun.stream.section.SectionHeader;
import com.cjun.stream.table.BaseTable;
import com.cjun.stream.table.TableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionAssembler {
    private static final Logger logger = LoggerFactory.getLogger(SectionAssembler.class);

    private ByteArrayOutputStream out;
    private int length;
    private int sumBytes;
    private List<BaseTable> tables = new ArrayList<>();
    private List<Section> section = new ArrayList<>();

    void accept(TsPacket packet) {
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
                sumBytes += packet.getLength();
            } else {
                if (out == null) {
                    return;
                }
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
        section.add(new Section(bytes));
    }

    public void clear() {
        if (tables != null) {
            tables.clear();
        }
    }
}
