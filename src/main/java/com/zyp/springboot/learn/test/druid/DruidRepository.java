package com.zyp.springboot.learn.test.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

@Repository
public class DruidRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DruidRepository(DataSource dataSource) { // Spring会自动注入配置好的Druid DataSource
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void someDatabaseOperation() {
        // 使用 jdbcTemplate 执行数据库操作...
        jdbcTemplate.queryForList("SELECT * FROM user");
    }
}