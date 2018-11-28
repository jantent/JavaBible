package com.sqlite;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author: tangJ
 * @Date: 2018/11/27 17:20
 * @description: 数据库初始化，创建表操作
 */
public class DbInit {

    private Logger logger = Logger.getLogger(DbInit.class.getName());
    private static final String dbName = "bible.db";
    private static final String sqlName = "sqlite.sql";


    private static class Inner {
        static DbInit sqliteHelper = new DbInit();
    }

    private DbInit() {
    }

    public static DbInit getInstance() {
        return Inner.sqliteHelper;
    }

    /**
     * 获取resource目录下的 sql文件
     *
     * @return
     */
    private String getSqlPath() {
        String filePath = this.getClass().getResource("/sql/" + sqlName).getPath();
        return filePath;
    }

    /**
     * 系统初始化，创建表
     *
     * @throws Exception
     */
    public void createTable() throws Exception {
        String sqlPath = getSqlPath();
        Map<String, SqlData> nameToTable = CreateDbUtil.parseFileToTable(sqlPath);

        Connection connection = DruidDb.getInstance().getConn();
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
