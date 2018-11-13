package com.spring.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author: tangJ
 * @Date: 2018/11/12 15:44
 * @description:
 */
@Service
public class LoginKit {

    public void login(LoginUser loginUser){
        System.out.println("login user ------> "+ loginUser);
    }
}
