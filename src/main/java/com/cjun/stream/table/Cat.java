package com.cjun.stream.table;

import com.cjun.stream.section.Section;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Cat extends BaseTable {
    private static final int CRC32_LENGTH = 4;
    static final int PID = 0x01;
    private List<Descriptor> descriptor;

    Cat(Section section) {
        setSection(section);
        parseDescriptor();
    }

    private void parseDescriptor() {
        byte[] sectionBody = getSection().getBody();

        int sectionLen = getHeader().getSection_length();
        if (descriptor == null) {
            descriptor = Lists.newArrayList();
        }

        for (int i = 0; i < sectionLen - CRC32_LENGTH - 1; ) {
            int descriptor_tag = sectionBody[i] & 0xff;
            int descriptor_length = sectionBody[i + 1] & 0xff;
            int CA_system_ID = sectionBody[i + 2] << 8 | sectionBody[i + 3] & 0xff;
            int reserved = (sectionBody[i + 4] >> 5) & 0x7;
            int CA_PID = (sectionBody[i + 4] << 8 | sectionBody[i + 5] & 0xff) & 0x1fff;
            byte[] private_data_byte = Arrays.copyOfRange(sectionBody, i + 6, i + 6 + descriptor_length);

            descriptor.add(new Descriptor(descriptor_tag, descriptor_length, CA_system_ID, reserved, CA_PID, private_data_byte));
            i += 6 + descriptor_length;
//            programs.add(new Program(Arrays.copyOfRange(sectionBody, i, i + 4)));
        }
    }

    public class Descriptor {
        int descriptor_tag;
        int descriptor_length;
        int CA_system_ID;
        int reserved;
        int CA_PID;
        byte[] private_data_byte;

        public Descriptor(int descriptor_tag, int descriptor_length, int CA_system_ID, int reserved, int CA_PID, byte[] private_data_byte) {
            this.descriptor_tag = descriptor_tag;
            this.descriptor_length = descriptor_length;
            this.CA_system_ID = CA_system_ID;
            this.CA_PID = CA_PID;
            this.private_data_byte = private_data_byte;
        }

        public int getDescriptor_tag() {
            return descriptor_tag;
        }

        public int getDescriptor_length() {
            return descriptor_length;
        }

        public int getCA_system_ID() {
            return CA_system_ID;
        }

        public int getCA_PID() {
            return CA_PID;
        }

        public byte[] getPrivate_data_byte() {
            return private_data_byte;
        }

        @Override
        public String toString() {
            return "Descriptor{" +
                    "descriptor_tag=" + descriptor_tag +
                    ", descriptor_length=" + descriptor_length +
                    ", CA_system_ID=" + CA_system_ID +
                    ", reserved=" + reserved +
                    ", CA_PID=" + CA_PID +
                    ", private_data_byte=" + Arrays.toString(private_data_byte) +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Cat{" +
                "descriptor=" + descriptor +
                '}';
    }
}
