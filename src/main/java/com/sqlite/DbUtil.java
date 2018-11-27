package com.sqlite;

import java.util.Map;

/**
 * @author: tangJ
 * @Date: 2018/11/27 17:13
 * @description:
 */
public class DbUtil {
    public synchronized void createTable(String tableName, String newTableName) {
//        try {
//
//            String dbType = DBCfg.getInstance().dbType;
//            String sqlFile = CreateDBUtil.getSqlFile(FindRoot.getWebRoot(), dbType);
//            Map<String, SqlData> nameToTable = CreateDBUtil.parseFileToTable(sqlFile);
//
//            if (!nameToTable.containsKey(tableName)) {
//                throw new Exception(String.format("%s 分表初始化脚本不存在", tableName));
//            }
//            SqlData sqlData = nameToTable.get(tableName);
//            String newTableSql = CreateDBUtil.updateTableName(sqlData.getTableSql(), tableName, newTableName);
//            Db.biz.executeUpdate(newTableSql);
//            for (String indexSql : sqlData.getIndexSql()) {
//                String newIndexSql = CreateDBUtil.updateTableIndexName(indexSql, tableName, newTableName);
//                Db.biz.executeUpdate(newIndexSql);
//            }
//            ExistTableName.getInstance().putTableName(newTableName);
//        } catch (Exception e) {
//            throw new IllegalStateException(String.format("%s 分表初始化失败", newTableName), e);
//        }
    }
}
