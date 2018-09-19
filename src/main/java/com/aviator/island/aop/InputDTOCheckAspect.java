package com.aviator.island.aop;

import com.aviator.island.constants.ResponseCode;
import com.aviator.island.entity.ResponseContent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 入参检查
 * Created by aviator_ls on 2018/8/19.
 */
@Aspect
@Component
public class InputDTOCheckAspect {

    @Around("execution(* com.aviator.island.controller.desk..*(..))")
    public Object check(ProceedingJoinPoint pj) throws Throwable {
        Object[] args = pj.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    ObjectError error = bindingResult.getAllErrors().get(0);
                    return new ResponseContent(ResponseCode.PARAMS_ERROR.getCode(), error.getDefaultMessage());
                }
            }
        }
        return pj.proceed();
    }
}
