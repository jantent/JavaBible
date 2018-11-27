package com.sqlite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tangJ
 * @Date: 2018/11/27 16:44
 * @description:
 */
public class SqlData {
    private String tableName;// 表名
    private String tableSql;// 表SQL
    private List<String> indexSql = new ArrayList<String>();// 索引SQL

    public SqlData() {
        super();
    }

    public SqlData(String tableName, String tableSql, List<String> indexSql) {
        super();
        this.tableName = tableName;
        this.tableSql = tableSql;
        this.indexSql = indexSql;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSql() {
        return tableSql;
    }

    public void setTableSql(String tableSql) {
        this.tableSql = tableSql;
    }

    public List<String> getIndexSql() {
        return indexSql;
    }

    public void setIndexSql(List<String> indexSql) {
        this.indexSql = indexSql;
    }

    public void addIndexSql(String indexSql) {
        this.indexSql.add(indexSql);
    }

    public String toString() {
        return "SQLData [tableName=" + tableName + ", tableSql=" + tableSql + ", indexSql=" + indexSql + "]";
    }
}
