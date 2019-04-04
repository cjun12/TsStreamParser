package com.cjun.stream.table;

import com.cjun.stream.section.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableFactory {
    private static final Logger logger = LoggerFactory.getLogger(TableFactory.class);

    public static BaseTable createTable(Section section) {
        BaseTable ret = null;
        logger.debug("{}",section.getHeader().getTable_id());
        switch (section.getHeader().getTable_id()) {
            case Pat.PID:
                ret = new Pat(section);
                break;
            case Cat.PID:
                ret = new Cat(section);
                logger.debug("{}", ret);
                break;
            default:
                ret = new BaseTable(section);
        }
        return ret;
    }

}
