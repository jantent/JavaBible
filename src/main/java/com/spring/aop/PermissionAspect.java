package com.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: tangJ
 * @Date: 2018/11/12 15:34
 * @description: 登录权限切面，模拟登录前，进行权限检查，如果权限足够，则允许登录，否则，抛出异常
 */
@Aspect
@Component
public class PermissionAspect {

    private int accessLevel = 5;

    @Pointcut("execution(public * com.spring.aop..*.*(..))")
    public void pointCut(){ }

    @Before("pointCut()")
    public void beforeMethod(JoinPoint joinPoint) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        for (Object arg:args){
            LoginUser user = (LoginUser) arg;
            if (user.getRoleLevel()>= accessLevel){
                System.out.println(methodName+" this user: "+user+ " has permission");
            }else {
                throw new Exception("this user: "+user+ "can not access");
            }
        }
    }

    @AfterReturning("pointCut()")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("after method:   "+methodName+" time :"+System.currentTimeMillis());
    }
}
