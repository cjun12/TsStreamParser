package com.cjun.stream;

import java.io.*;

import com.cjun.stream.packet.TsPacket;
import com.cjun.stream.parser.TsPacketParser;
import com.cjun.stream.section.Section;
import com.cjun.stream.section.SectionAssembler;

public class Main {
    public static void main(String[] args) {
        String path = "../asserts/TsStream.ts";
        //TODO: 数据校验
        if (args.length > 0){
            path = args[0];
        }
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            TsPacketParser parser = new TsPacketParser(in);
            parser.start();
            TsPacket temp;
            int i = 0;
            SectionAssembler assembler = new SectionAssembler();
            while ((temp = parser.getTsPacket()) != null) {
            }
        } catch (FileNotFoundException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
