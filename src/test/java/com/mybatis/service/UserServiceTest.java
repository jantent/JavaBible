package com.mybatis.service;

import com.mybatis.Db;
import com.mybatis.dao.UserDao;
import com.mybatis.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author tangj
 * @date 2018/12/16 20:51
 * @description:
 */
public class UserServiceTest {
    SqlSession sqlSession = null;
    UserDao userDao = null;
    @Before
    public void getSqlSession(){
        sqlSession = Db.getInstance().getSqlSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @Test
    public void testFindUserById() throws Exception{
        User user = userDao.findUserById(1);
        System.out.println(user);
    }

    @Test
    public void testFindUserByName() throws Exception{
        String userName = "小明";
        List<User> userList = userDao.findUserByName(userName);
        System.out.println(userList);
    }

}