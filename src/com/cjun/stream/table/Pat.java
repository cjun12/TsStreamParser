package com.cjun.stream.table;

import java.util.ArrayList;
import java.util.List;

import com.cjun.stream.Program;

public class Pat {
	public static final int PID = 0x0000;
	private List<Program> programs;

	public Pat(byte[] data) {
		int length = data.length;
		int pos = 0;
		if (programs == null) {
			programs = new ArrayList<>();
		}
		programs.clear();
		int number;
		int resevered;
		int pid;
		while (pos + 4 <= length) {
			number = data[pos++] << 8 | data[pos++];
			resevered = data[pos] >> 5;
			pid = (data[pos++] << 8 | data[pos++]) & 0x1ffff;
			programs.add(new Program(number,resevered,pid));
		}
	}
}
