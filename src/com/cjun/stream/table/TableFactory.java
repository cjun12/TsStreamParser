package com.cjun.stream.table;

import com.cjun.stream.section.Section;

public class TableFactory {
	public static AbstractTable createTable(Section section){
		AbstractTable ret = null;
		switch(section.getHeader().getTable_id()){
			case Pat.PID:
				ret = new Pat(section);
				break;
			default:
				ret = null;
		}
		return ret;
	}
}
