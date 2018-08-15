package com.cjun.stream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.cjun.stream.packet.TsPacket;
import com.cjun.stream.parser.TsPacketParser;
import com.cjun.stream.section.Section;
import com.cjun.stream.section.SectionAssembler;

public class Main {
	public static void main(String[] args) {
		if(args.length<0)
			return ;
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(args[0])) ;
			TsPacketParser parser = new TsPacketParser(in);
			parser.start();
			TsPacket temp;
			int i = 0;
			SectionAssembler assembler = new SectionAssembler();
			while( (temp=parser.getTsPacket())!=null){
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
