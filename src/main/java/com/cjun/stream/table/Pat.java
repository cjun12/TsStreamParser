package com.cjun.stream.table;

import java.util.ArrayList;
import java.util.List;

import com.cjun.stream.Program;
import com.cjun.stream.section.Section;
import com.cjun.stream.section.SectionHeader;

public class Pat extends AbstractTable{
	public static final int PID = 0x0000;
	private List<Program> programs;

	public Pat(Section section){
		setSection(section);
	}
	
	
}
