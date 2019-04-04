package com.cjun.stream.table;

import com.cjun.stream.bean.Program;
import com.cjun.stream.section.Section;
import com.cjun.stream.utils.StringUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Pat extends BaseTable {
    private static final Logger logger = LoggerFactory.getLogger(Pat.class);

    static final int PID = 0x0000;
    private static final int CRC32_LENGTH = 4;
    private List<Program> programs;

    public Pat(Section section) {
        setSection(section);
        parseProgram();
    }

    private void parseProgram() {
        int sectionLen = getHeader().getSection_length();
        if (programs == null) {
            programs = Lists.newArrayList();
        }
        byte[] sectionBody = getSection().getBody();
        if (sectionBody.length + 1 < sectionLen) {
            logger.debug("{}", getSection().getHeader());
        }
        for (int i = 0; i + 3 < sectionLen - CRC32_LENGTH - 1; i += 4) {
            int program_number = ((sectionBody[i] << 8) | sectionBody[i + 1] & 0xff) & 0xffff;
            int reserved = (sectionBody[i + 2] >> 5) & 0x7;
            int program_map_PID = ((sectionBody[i + 2] << 8) | sectionBody[i + 3] & 0xff) & 0x1fff;
            programs.add(new Program(program_number, reserved, program_map_PID));
//            programs.add(new Program(Arrays.copyOfRange(sectionBody, i, i + 4)));
        }
    }

    @Override
    public String toString() {
        return "Pat{" +
                "programs=" + programs +
                '}';
    }
}
