package com.sqlite;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: tangJ
 * @Date: 2018/11/28 10:24
 * @description: 数据库连接池
 */
public class DruidDb {

    private Logger logger = Logger.getLogger(this.getClass());
    private DruidDataSource dataSource = new DruidDataSource();
    private static final String dbName = "bible.db";
    private static final String sqlName = "sqlite.sql";


    private static class Inner {
        static DruidDb druidDb = new DruidDb();
    }

    private DruidDb() {
        init();
    }

    public static DruidDb getInstance() {
        return Inner.druidDb;
    }

    private void init() {
        // JDBC驱动
        dataSource.setDriverClassName("org.sqlite.JDBC");
        // 数据库初始化验证
        dataSource.setValidationQuery("select 1");
        // 数据库地址
        dataSource.setUrl("jdbc:sqlite:" + dbName);
        // 连接池最大数量
        dataSource.setMaxActive(10);
        // 连接池最小数量
        dataSource.setMinIdle(1);
        // 获取连接的最大等待时间
        dataSource.setMaxWait(1 * 1000L);
        // 初试创建时连接池的数量
        dataSource.setInitialSize(5);
    }


    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public Connection getConn() throws Exception {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            logger.info("get connection successFully");
            return connection;
        } catch (SQLException e) {
            logger.info(e.getSQLState());
            throw e;
        }
    }

    public int getSize() {
        return dataSource.getPoolingCount();
    }
}
