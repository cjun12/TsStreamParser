package com.cjun.stream.table;

import com.cjun.stream.section.Section;
import com.cjun.stream.section.SectionHeader;

public class BaseTable {
    private Section section;

    BaseTable() {
    }

    BaseTable(Section section) {
        this.section = section;
    }

    Section getSection() {
        return section;
    }

    void setSection(Section section) {
        this.section = section;
    }

    SectionHeader getHeader() {
        return this.section.getHeader();
    }

    public int getPid() {
        return this.section.getHeader().getTable_id();
    }

    @Override
    public String toString() {
        return "BaseTable{" +
                "section=" + section +
                '}';
    }
}
