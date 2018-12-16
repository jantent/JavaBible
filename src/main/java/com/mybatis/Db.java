package com.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author tangj
 * @date 2018/12/16 20:33
 * @description:
 */
public class Db {

    private static final Logger LOGGER = LoggerFactory.getLogger(Db.class);

    private SqlSessionFactory sqlSessionFactory = null;
    private SqlSession sqlSession = null;

    private void init() {
        //mybatis配置文件
        String resource = "mybatis/mybatis-config.xml";
        //得到配置文件输入流
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建会话工厂,传入，mybatis的配置信息
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    private static Db instance = new Db();

    private Db() {
        init();
    }

    public static Db getInstance() {
        return instance;
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

}
