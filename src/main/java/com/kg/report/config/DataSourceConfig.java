package com.kg.report.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.sql.SQLUtils;
import com.kg.report.config.filter.MySlf4jLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public Slf4jLogFilter slf4jLogFilter () {
        Slf4jLogFilter logFilter = new MySlf4jLogFilter();
        logFilter.setStatementExecutableSqlLogEnable(true);
        logFilter.setStatementSqlFormatOption(new SQLUtils.FormatOption(true, true));
        return logFilter;
    }
}
