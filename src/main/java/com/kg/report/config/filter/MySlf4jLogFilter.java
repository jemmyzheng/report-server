package com.kg.report.config.filter;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;

public class MySlf4jLogFilter extends Slf4jLogFilter {

    @Override
    protected void statementLog(String message) {
        if (message.indexOf("created") > 0) {
            return;
        }
        message = message.replaceAll("\\s+", " ");
        super.statementLog(message);
    }

}
