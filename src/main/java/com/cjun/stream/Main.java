package com.cjun.stream;

import java.io.*;

import com.cjun.stream.controller.PacketDispatcher;
import com.cjun.stream.controller.TsPacketParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String path = "../asserts/TsStream.ts";

        //TODO: 数据校验
        if (args.length > 0) {
            path = args[0];
        }
        TsPacketParser parser = new TsPacketParser(path);
        PacketDispatcher packetDispatcher = new PacketDispatcher(parser);
        parser.start();
        packetDispatcher.start();
    }
}
