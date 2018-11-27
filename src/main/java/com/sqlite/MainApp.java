package com.sqlite;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author: tangJ
 * @Date: 2018/11/27 10:36
 * @description:
 */
public class MainApp {


    public static void main(String args[]) throws Exception{
        SqliteHelper.getInstance().createTable();

    }


}
