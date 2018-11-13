package com.spring.aop;

/**
 * @author: tangJ
 * @Date: 2018/11/12 15:49
 * @description: aop例子：登录用户
 */
public class LoginUser {
    private String name;

    private int roleLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "name='" + name + '\'' +
                ", roleLevel=" + roleLevel +
                '}';
    }
}
