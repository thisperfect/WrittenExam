package com.dayee.writtenExam.mongo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yq.song on 2017/1/17.
 */
@Aspect
@Component
public class CompensateAdvisor{
    
    private static final Logger log = LoggerFactory.getLogger(CompensateAdvisor.class);
    
    @Autowired
    private CompensationManager manager;
    
    //补偿切点
    @Pointcut("@annotation(com.dayee.writtenExam.mongo.Compensate)")
    public void mongoMethodTx(){}
    
    //环绕通知
    @Around("mongoMethodTx()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        
        try {
            log.info("---- Compensating Func {} ----", pjp.getSignature().toShortString());
            Object ret = pjp.proceed();
            manager.enlist(pjp); // this step has been performed, subject to compensate
            return ret;
        }finally {
            manager.resetTx();
            log.info("---- END METHOD Compensating {} ----", pjp.getSignature().toShortString());
        }
    }
    
}
