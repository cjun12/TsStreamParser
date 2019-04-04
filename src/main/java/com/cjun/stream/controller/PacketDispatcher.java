package com.cjun.stream.controller;

import com.cjun.stream.packet.TsPacket;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PacketDispatcher extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(PacketDispatcher.class);

    TsPacketParser packetParser;
    Map<Integer, SectionAssembler> assemblerMap = Maps.newHashMap();

    public PacketDispatcher(TsPacketParser tsPacketParser) {
        packetParser = tsPacketParser;
    }

    @Override
    public void run() {
        TsPacket tsPacket;
        int pid;
        SectionAssembler assembler = new SectionAssembler();
        try {
            while ((tsPacket = packetParser.getTsPacket()) != null) {
                pid = tsPacket.getHeader().getPid();
                if ((assembler = assemblerMap.get(pid)) == null) {
                    assembler = new SectionAssembler();
                    assemblerMap.put(pid, assembler);
                }
                logger.debug("{}", pid);
                assembler.accept(tsPacket);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
