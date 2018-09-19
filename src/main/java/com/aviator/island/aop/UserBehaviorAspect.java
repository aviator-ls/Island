package com.aviator.island.aop;

import com.aviator.island.annotation.UserBehavior;
import com.aviator.island.constants.ResponseCode;
import com.aviator.island.entity.ResponseContent;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by aviator_ls on 2018/9/19.
 */
@Aspect
@Component
public class UserBehaviorAspect {

    @Autowired
    private UserService<User> userService;

    //切入点
    @Pointcut(value = "@annotation(com.aviator.island.annotation.UserBehavior)")
    private void pointcut() {
    }

    @Around("execution(* com.aviator.island.controller.desk..*(..)) && pointcut() && @annotation(rl)")
    public Object check(ProceedingJoinPoint pj, UserBehavior rl) throws Throwable {
        Object result = pj.proceed();
        int credit = rl.credit();
        if (result instanceof ModelAndView) {
            updateCredit(credit);
        }
        if (result instanceof ResponseContent) {
            ResponseContent responseContent = (ResponseContent) result;
            if (responseContent != null) {
                if (ResponseCode.SUCCESS.getCode().equals(responseContent.getResponseCode())) {
                    updateCredit(credit);
                }
            }
        }
        return result;
    }

    private void updateCredit(int credit) {
        if (credit != 0) {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                String userName = (String) subject.getPrincipal();
                if (StringUtils.isNotBlank(userName)) {
                    User user = userService.getUserByUserName(userName);
                    int newCredit = user.getCredit() + credit;
                    user.setCredit(newCredit);
                    userService.update(user);
                }
            }
        }
    }
}
