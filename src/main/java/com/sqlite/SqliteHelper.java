package com.sqlite;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author: tangJ
 * @Date: 2018/11/27 17:20
 * @description:
 */
public class SqliteHelper {

    private Connection connection = null;
    private Logger logger = Logger.getLogger(SqliteHelper.class.getName());
    static final String dbName = "bible.db";


    private void init() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        logger.info("Opened database successfully");
    }

    private static class Inner {
        static SqliteHelper sqliteHelper = new SqliteHelper();
    }

    private SqliteHelper() {
        try {
            init();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public static SqliteHelper getInstance() {
        return Inner.sqliteHelper;
    }

    public Connection getConnection() {
        return connection;
    }

    static final String sqlName = "sqlite.sql";

    /**
     * 获取resource目录下的 sql文件
     *
     * @return
     */
    public String getSqlPath() {
        String filePath = this.getClass().getResource("/sql/" + sqlName).getPath();
        return filePath;
    }

    public void createTable() throws Exception {
        String sqlPath = getSqlPath();
        Map<String, SqlData> nameToTable = CreateDbUtil.parseFileToTable(sqlPath);

        Connection connection = SqliteHelper.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        for (Map.Entry<String, SqlData> entry : nameToTable.entrySet()) {
            String tableName = entry.getKey();

            boolean isExist = validateTableExist(connection, tableName);
            if (!isExist) {
                SqlData sqlData = entry.getValue();
                stmt.executeUpdate(sqlData.getTableSql());
            }
        }
    }

    /**
     * 校验数据库表中是否存在
     *
     * @param connection
     * @param tableName
     * @return
     */
    private boolean validateTableExist(Connection connection, String tableName) {
        String[] types = {"TABLE"};
        ResultSet tabs = null;
        try {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            tabs = dbMetaData.getTables(null, tableName, tableName, types);
            return tabs.next();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return false;
    }
}
