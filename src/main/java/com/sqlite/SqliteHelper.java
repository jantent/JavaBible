package com.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

/**
 * @author: tangJ
 * @Date: 2018/11/27 17:20
 * @description:
 */
public class SqliteHelper {

    private Connection connection  = null;
    private Logger logger = Logger.getLogger(SqliteHelper.class.getName());
    static final String dbName = "bible.db";


    private void init() throws Exception{
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbName);
        } catch ( Exception e ) {
            logger.info(e.getMessage());
        }
        logger.info("Opened database successfully");
    }

    private static class Inner{
        static SqliteHelper sqliteHelper = new SqliteHelper();
    }

    private SqliteHelper(){
        try {
            init();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public static SqliteHelper getInstance(){
        return Inner.sqliteHelper;
    }

    public Connection getConnection(){
        return connection;
    }
}
