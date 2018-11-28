package com.sqlite;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: tangJ
 * @Date: 2018/11/27 16:43
 * @description: 数据库创建工具
 */
public class CreateDbUtil {
    public static Map<String, SqlData> parseFileToTable(String sqlFilePath) throws Exception {
        Map<String, SqlData> nameToTable = new HashMap<String, SqlData>();

        FileInputStream inSql = new FileInputStream(sqlFilePath);
        byte[] content = new byte[inSql.available()];
        inSql.read(content);
        inSql.close();

        String[] sqls = (new String(content, "UTF-8")).split(";");
        for (int i = 0; i < sqls.length; i++) {
            String sql = sqls[i];
            if (sql.trim().length() < 10) {// 如果是空行之类
                continue;
            }
            if (sql.indexOf("^") != -1) {// 如果是碰到SET TERM
                // ^则下句SQL需要重构,否则转下执行
                if ("SET TERM ^".equalsIgnoreCase(sql.trim()) && sqls.length > i + 1) {
                    sqls[i + 1] = sqls[i + 1] + ";END";
                    continue;
                } else if ("^".equals(sql.trim().substring(0, 1))) {
                    sql = sql.trim().substring(1);
                    if (sql.trim().length() < 10) {// 如果是空行之类
                        continue;
                    }

                } else {
                    continue;
                }

            }
            // 解析SQL，获取表明到TABLE语句
            addSqlData(nameToTable, sql);
        }

        return nameToTable;
    }

    /**
     * 解析数据库表名
     *
     * @param sql
     * @return
     */
    private static final String parseTableToName(String sql) {
        // 解析SQL获取其中的表面
        int startIndex = sql.indexOf(" TABLE ");
        if (startIndex < 0) {
            // 不存在则返回空
            return null;
        }
        int endIndex = sql.indexOf("(");
        if (endIndex < 0) {
            // 错误的SQL
            return null;
        }
        return sql.substring(startIndex + 7, endIndex).trim();
    }

    /**
     * 解析索引名称
     *
     * @param sql
     * @return
     */
    private static final String parseIndexToName(String sql) {
        int startIndex = sql.indexOf(" INDEX ");
        if (startIndex < 0) {
            // 不是索引则直接返回
            return null;
        }
        startIndex = sql.indexOf(" ON ");
        if (startIndex < 0) {
            // 索引不正确
            return null;
        }
        int endIndex = sql.indexOf("(");
        if (endIndex < 0) {
            // 错误的SQL
            return null;
        }
        return sql.substring(startIndex + 4, endIndex).trim();
    }


    /**
     * 构造SqlData对象,封装建表语句
     *
     * @param nameToTable
     * @param sql
     */
    private static final void addSqlData(Map<String, SqlData> nameToTable, String sql) {
        SqlData sqlData;
        String tableName = parseTableToName(sql);
        if (tableName != null) {
            sqlData = nameToTable.get(tableName);
            if (sqlData == null) {
                sqlData = new SqlData();
            }
            sqlData.setTableName(tableName);
            sqlData.setTableSql(sql);
            nameToTable.put(tableName, sqlData);
        }
        tableName = parseIndexToName(sql);
        if (tableName != null) {
            sqlData = nameToTable.get(tableName);
            if (sqlData == null) {
                sqlData = new SqlData();
            }
            sqlData.setTableName(tableName);
            sqlData.addIndexSql(sql);
            nameToTable.put(tableName, sqlData);
        }
    }
}
