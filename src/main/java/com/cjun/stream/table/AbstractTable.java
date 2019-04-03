package com.cjun.stream.table;

import com.cjun.stream.section.Section;
import com.cjun.stream.section.SectionHeader;

public abstract class AbstractTable {
	private Section section;

	protected Section getSection() {
		return section;
	}

	protected void setSection(Section section) {
		this.section = section;
	}
	
	public SectionHeader getHeader(){
		return this.section.getHeader();
	}
	
	public int getPid(){
		return this.section.getHeader().getTable_id();
	}
}
