package com.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author: tangJ
 * @Date: 2018/11/27 10:36
 * @description:
 */
public class SqliteJdbc {

    static final String dbName = "bible.db";
    static final String sqlName = ".src/main/resources/sql/sqlite.sql";

    public static void main(String args[]){
        Connection connection = SqliteHelper.getInstance().getConnection();
        File file = new File(sqlName);
        System.out.println(file.exists());
    }
}
