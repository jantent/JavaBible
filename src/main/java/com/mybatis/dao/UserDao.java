package com.mybatis.dao;

import com.mybatis.entity.User;

import java.util.List;

/**
 * @author tangj
 * @date 2018/12/16 20:01
 * @description:
 */
public interface UserDao {

    User findUserById(int id) throws Exception;

    List<User> findUserByName(String userName) throws Exception;

    void insertUser(User user) throws Exception;
}
