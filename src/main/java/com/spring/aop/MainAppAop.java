package com.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;

/**
 * @author: tangJ
 * @Date: 2018/11/12 15:50
 * @description:
 */
public class MainAppAop {

    private static Random random = new Random();

    public static void main(String args[]){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(PermissionAspect.class);
//        context.register(LoginKit.class);
//        context.refresh();

        LoginKit loginKit = context.getBean(LoginKit.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setName("test");
        loginUser.setRoleLevel(random.nextInt(10));
        loginKit.login(loginUser);
    }
}
