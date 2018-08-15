package com.cjun.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class MyTest {
	public static void main(String[] args) {
		byte[] headerBytes = {0x11,(byte) 0xa9};
		int ret =(headerBytes[0] << 8 | headerBytes[1]) & 0x1fff;
		System.out.println(headerBytes[0] << 8);
		System.out.println(headerBytes[1]&0xff);
	}
}
