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
 * 事物补偿切面
 * Created by yq.song on 2017/1/17.
 */
@Aspect
@Component
public class CompensatableAdvisor {
	
	private static final Logger log = LoggerFactory.getLogger(CompensatableAdvisor.class);
	
	@Autowired
	private CompensationManager manager;
	
	//补偿切点
	@Pointcut("@annotation(com.dayee.writtenExam.mongo.Compensatable)")
	public void mongoMethodTx(){}
	
	//环绕通知
	@Around("mongoMethodTx()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		
		manager.startTx();
		
		Object ret = null;
		try {
			
			log.debug("#### START COMPENSATABLE TX {} ####", pjp.getSignature());
			ret = pjp.proceed();
			//manager.resetTx();
		}catch(Throwable t){
			manager.doCompensation();
			throw t;
		}finally {
			//manager.resetTx();
			log.debug("#### END COMPENSATABLE TX {} #####",  pjp.getSignature());
		}
		return ret;
	}
	
}
