package com.cjun.stream.controller;

import com.cjun.stream.packet.TsPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.cjun.stream.packet.TsPacketHeader.SYNC_BYTE;

public class TsPacketParser extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TsPacketParser.class);

    private static final int NOT_FIND_SYNC = -1;
    private static final int TS_PACKET_LENGTH_SD = 188;
    private static final int TS_PACKET_LENGTH_HD = 204;
    private int tsPacketLength = 0;
    private int firstPacketSyncPos = NOT_FIND_SYNC;

    private LinkedBlockingQueue<TsPacket> queue = new LinkedBlockingQueue<>(16);
    private byte[] buf;
    private String path;
    private InputStream source;
    private File sourceFile;

    public TsPacketParser(String path) {
        // TODO Auto-generated constructor stub
        this.path = path;
    }

    @Override
    public void run() {
        logger.debug("TsPacketParser start");
        initStream();
        try {
            if (firstPacketSyncPos == NOT_FIND_SYNC) {
                findFirstSyncPos(0);
            }
            source.reset();
            long num = source.skip(firstPacketSyncPos);
            logger.debug("skip {} byte", num);
            int aByte;
            int pos = 0;
            while ((aByte = source.read()) != -1) {
                if (aByte == SYNC_BYTE && (pos >= tsPacketLength || pos == 0)) {
                    if (buf != null && buf[0] == SYNC_BYTE) {
                        queue.offer(new TsPacket(buf, pos), 30, TimeUnit.SECONDS);
                    }
                    buf = new byte[tsPacketLength];
                    pos = 0;
                }
                if (pos >= buf.length)
                    return;
                buf[pos++] = (byte) (aByte & 0xff);
            }
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                source.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initStream() {
        sourceFile = new File(path);
        try {
            if (sourceFile.exists()) {
                source = new BufferedInputStream(new FileInputStream(sourceFile));
                source.mark(2048);
            } else {
                logger.error("{} no found", path);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void findFirstSyncPos(int start) {
        try {
            if (start != 0) {
                source.reset();
            }
            source.skip(start);
            firstPacketSyncPos = 0;
            while (source.read() != SYNC_BYTE) {
                firstPacketSyncPos++;
            }
            logger.debug("find sync byte at pos {}", firstPacketSyncPos);
            if (!checkFollowPackage(TS_PACKET_LENGTH_SD) && !checkFollowPackage(TS_PACKET_LENGTH_HD)) {
                findFirstSyncPos(firstPacketSyncPos + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkFollowPackage(int length) throws IOException {
        long tsSize = sourceFile.length();
        logger.debug("file size is {}", tsSize);
        int size = (int) Math.min(TS_PACKET_LENGTH_HD * 5, tsSize - firstPacketSyncPos);
        byte[] buf = new byte[size];
        source.read(buf, 1, size - 1);
        for (int i = 1; i < 5 && length * i < size; i++) {
            if (buf[length * i] == SYNC_BYTE) {
                tsPacketLength = length;
            } else {
                return false;
            }
        }
        return true;
    }

    public TsPacket getTsPacket() throws InterruptedException {
        return queue.poll(30, TimeUnit.SECONDS);
    }
}
