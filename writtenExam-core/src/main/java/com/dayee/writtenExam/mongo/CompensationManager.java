package com.dayee.writtenExam.mongo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class CompensationManager{
	private static final Logger log = LoggerFactory.getLogger(CompensationManager.class);
	static final ThreadLocal userThreadLocal = new ThreadLocal();
    static final AtomicLong idGenerator = new AtomicLong(10000);
    public CompensatableTx startTx() {
        long uid = idGenerator.getAndIncrement();
    	CompensatableTx tx = new CompensatableTx(uid);
        userThreadLocal.set(tx);
        log.debug("TX"+tx.getId()+" started.");
        return tx;
    }

    public void resetTx() {
        userThreadLocal.remove();
    }
    public void enlist(ProceedingJoinPoint invocation){
    	getTx().add(invocation);
    }
    public long getTransactionId(){
        return getTx().getId();
    }
    public boolean isCompensating(){
        return getTx().isCompensating();
    }
    public CompensatableTx getTx() {
        return (CompensatableTx)userThreadLocal.get();
    }
    void doCompensation(){
    	CompensatableTx tx = getTx();
    	List<ProceedingJoinPoint> invocations = tx.getJoinPoints();
        tx.setCompensating(true);
    	for(int i=invocations.size()-1;i>=0;i--){
			ProceedingJoinPoint joinPoint = invocations.get(i);
    		log.debug("Compensating {}", joinPoint.getSignature().getName());
    		Method compensator = null;
    		Object target = joinPoint.getTarget();
    		Method[] methods = target.getClass().getMethods();
    		for(int k=0; methods!=null && k<methods.length;k++){
    			Method m = methods[k];
    			if(m.getName().equals( joinPoint.getSignature().getName()+"_compensator")){
    				compensator = m;
    				break;
    			}    				
    		}
    		if(compensator == null)
    			log.warn("Compensator for {}"+ joinPoint.getSignature().getName() +" does not exist.");
    		else{
    			try{
    				compensator.invoke(target,joinPoint.getArgs());	
    			}
    			catch(IllegalAccessException ile){
    				/**@todo: ignorable */
    				log.error(ile.toString());
    			}
    			catch(java.lang.reflect.InvocationTargetException ite){
    				/**@todo: ignorable */
    				log.error(ite.toString());	
    			}
    			
    		}
    	}
    }
}

class CompensatableTx {
	private static final Logger log = LoggerFactory.getLogger(CompensatableTx.class);
    
	private long id;
    private boolean compensating;
	private ArrayList<ProceedingJoinPoint> joinPoints = new ArrayList<ProceedingJoinPoint>();
    public CompensatableTx(long uid){
        this.id = uid;
    }
	public long getId(){
		return id;
	}
	public void add(ProceedingJoinPoint joinPoint){
        /**@todo: check log level first */
		log.trace("TX"+getId()+": ---- enlisting {} ----", joinPoint.getSignature().toShortString());
		joinPoints.add(joinPoint);
	}
	public ArrayList<ProceedingJoinPoint> getJoinPoints(){
		return joinPoints;
	}
    void setCompensating(boolean status){
        this.compensating = status;
    }
    boolean isCompensating(){
        return true;
    }
}