package com.dayee.writtenExam.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.dayee.writtenExam.framework.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {
	
	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	//jedis连接池
	@Autowired
	private JedisPool jedisPool;
	
	private static RedisUtil redisUtil;
	
	@PostConstruct
	public void init(){
		redisUtil=this;
		redisUtil.jedisPool=this.jedisPool;
	}
	
	/**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && redisUtil.jedisPool !=null) {
			redisUtil.jedisPool.returnResource(jedis);
        }
    }
	
	/**
	 * 队列从左端入队
	 * @param key
	 * @param value
	 * @return
	 */
	public static long LpushList(String key,String value){
		Jedis jedis = null;
		long result=0;
		try {
			jedis = redisUtil.jedisPool.getResource();
			result=jedis.lpush(key,value);
		} catch (Exception e) {
			if(logger.isDebugEnabled()){
				logger.debug("Redis队列入队出错：[key,value]:{}",new Object[]{key,value},e);
			}
		}finally{
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 队列以阻塞方式右端出队
	 * @param key
	 * @return
	 */
	public static List<String> BrpopList(String key){
		Jedis jedis = null;
		List<String> result=null;
		try {
			jedis = redisUtil.jedisPool.getResource();
			result=jedis.brpop(0,key);
		} catch (Exception e) {
			if(logger.isDebugEnabled()){
				logger.debug("Redis队列{}出队出错",key,e);
			}
		}finally{
			returnResource(jedis);
		}
		return result;
	}
	
    /**
     * 删除某个key
     * @param key
     * @return
     */
    public static long delKey(String ...key){
    	
    	Jedis jedis = null;
    	long result=0;
    	try {  
    		jedis = redisUtil.jedisPool.getResource(); 
    		result=jedis.del(key);
    	} catch (Exception e) { 
    		if(logger.isDebugEnabled()){
    			logger.debug("Redis删除key出错：key为{}",key,e);
    		}
    		e.printStackTrace();
    	}finally{
    		returnResource(jedis);
    	}
    	return result;
    }
	
    /**
     * 递增某个整数key
     * @param key
     * @return
     */
    public static long incrKey(String key){
    	
    	Jedis jedis = null;
    	long result=0;
    	try {  
    		jedis = redisUtil.jedisPool.getResource(); 
    		result=jedis.incr(key);
    	} catch (Exception e) { 
    		if(logger.isDebugEnabled()){
    			logger.debug("Redis递增key出错：key为{}",key,e);
    		}
    		e.printStackTrace();
    	}finally{
    		returnResource(jedis);
    	}
    	return result;
    }
    /**
     * 判断某个key值是否存在
     * @param key
     * @return
     */
    public static boolean existsKey(String key){
    	 
    	 boolean exist=false;
         Jedis jedis = null;
         try {  
            jedis = redisUtil.jedisPool.getResource(); 
            exist=jedis.exists(key);
         } catch (Exception e) { 
        	 if(logger.isDebugEnabled()){
        		 logger.debug("Redis判断key：{}是否存在时出错",key,e);
        	 }
        	 e.printStackTrace();
         }finally{
             returnResource(jedis);
         }
    	return exist;
    }
    /**
     * 查询匹配的key
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern){
    	 
		Set<String> set=null;
         Jedis jedis = null;
         try {  
            jedis = redisUtil.jedisPool.getResource(); 
            set= jedis.keys(pattern);
         } catch (Exception e) { 
        	 if(logger.isDebugEnabled()){
        		 logger.debug("Redis查询匹配key：{}时出错",pattern, e);
        	 }
        	 e.printStackTrace();
         }finally{
             returnResource(jedis);
         }
    	return set;
    }
    /**
     * 删除key
     * @param keys
     * @return
     */
    public static void delKeys(String ...keys){
    	
    	Jedis jedis = null;
    	try {  
    		jedis = redisUtil.jedisPool.getResource();
    		jedis.del(keys);
    	} catch (Exception e) { 
    		if(logger.isDebugEnabled()){
    			logger.debug("Redis删除key：{}时出错",keys, e);
    		}
    		e.printStackTrace();
    	}finally{
    		returnResource(jedis);
    	}
    }
    /**
     * 设置 String
     * @param key
     * @param value
     */
    public static void setString(String key ,String value){
    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           value=value==null?"":value;
           jedis.set(key,value);
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
	        	if(existsKey(key)){
	    			logger.debug("Redis修改String类型出错,key为{},value原值为{}",new Object[]{key,jedis.get(key)},e);
	    			logger.debug("Redis修改String类型出错,key为{},value修改为{}",new Object[]{key,value},e);
	    		}else{
	    			logger.debug("Redis新增String类型出错,key为{},value为{}",new Object[]{key,value},e);
	    		}
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
    }

	/**
	 * 设置java对象
	 */
	public static void setJavaObject(Object keyObject, Object valueObject) {

		if (keyObject == null) {
			return;
		}

		Jedis jedis = null;
		byte[] key = (keyObject instanceof String) ?
					 keyObject.toString().getBytes() : SerializeUtils.serialize(keyObject);

		try {
			jedis = redisUtil.jedisPool.getResource();

			jedis.set(key,SerializeUtils.serialize(valueObject));
		} catch (Exception e) {
			if(logger.isDebugEnabled()){
				if(existsKey(new String(key))){
					logger.debug("Redis修改String类型出错,key为{},value原值为{}",new Object[]{key,jedis.get(key)},e);
					logger.debug("Redis修改String类型出错,key为{},value修改为{}",new Object[]{key,valueObject.toString()},e);
				}else{
					logger.debug("Redis新增String类型出错,key为{},value为{}",new Object[]{key,valueObject.toString()},e);
				}
			}
			e.printStackTrace();
		}finally{
			returnResource(jedis);
		}
	}

    /**
     * 获取String值
     * @param key
     * @return value
     */
    public static String getString(String key){
    	String result=null;
    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           result =jedis.get(key);
           if(logger.isTraceEnabled()){
        	   jedis.incr(Constants.REDIS_SEARCH_COUNT);
        	   logger.trace("查询redis次数：{}",jedis.get(Constants.REDIS_SEARCH_COUNT));
           }
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
        		logger.debug("Redis获取String类型出错,key为{}",key,e);
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
        
    	return result;
    }
    
    /**
     * 设置 HASH
     * @param key
     * @param value
     */
    public static void hsetHash(String key ,String field,String value){

    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           value=value==null?"":value;
           jedis.hset(key, field, value);
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
	        	if(existsKey(key)){
	    			logger.debug("Redis修改hash类型出错,key为{},field为{},value原值为{}" ,new Object[]{ key, field, jedis.hget(key, field)} );
	    			logger.debug("Redis修改hash类型出错,key为{},field为{},value修改为{}",new Object[]{key,field,value},e);
	        	}else{
	        		logger.debug("Redis新增hash类型出错,key为{},field为{},value为{}",new Object[]{key,field,value},e);
	        	}
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
    	
    }
    /**
     * 获取 HASH单个属性
     * @param key
     * @param field
     */
    public static String hgetHash(String key ,String field){
    	String result=null;
    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           result =jedis.hget(key, field);
           if(logger.isTraceEnabled()){
        	   jedis.incr(Constants.REDIS_SEARCH_COUNT);
        	   logger.trace("查询redis次数：{}",jedis.get(Constants.REDIS_SEARCH_COUNT));
           }
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
        		logger.debug("Redis获取hash类型出错,key为{}field为{}",new Object[]{key,field},e);
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
        
    	return result;
    }
    /**
     * 获取 HASH全部属性
     * @param key
     */
    public static Map<String, String> hgetAllHash(String key){
    	Map<String, String> map=null;
    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           map =jedis.hgetAll(key);
           if(logger.isTraceEnabled()){
        	   jedis.incr(Constants.REDIS_SEARCH_COUNT);
        	   logger.trace("查询redis次数：{}",jedis.get(Constants.REDIS_SEARCH_COUNT));
           }
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
        		logger.debug("Redis获取hash类型出错,key为{}的全部filed和value对应的值",key,e);
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
        
    	return map;
    }
    
    /**
     * 设置 SET
     * @param key
     * @param value
     */
    public static void saddSet(String key ,String value){
    	
    	Jedis jedis = null;
    	try {  
    		jedis = redisUtil.jedisPool.getResource(); 
    		value=value==null?"":value;
    		jedis.sadd(key, value);
    	} catch (Exception e) { 
    		if(logger.isDebugEnabled()){
	    		if(existsKey(key)){
	    			logger.debug("Redis修改Set类型出错,key为{},value修改为{}",new Object[]{key,value},e);
	    		}else{
	    			logger.debug("Redis新增Set类型出错,key为{},value为{}",new Object[]{key,value},e);
	    		}
    		}
    		e.printStackTrace();
    	}finally{
    		returnResource(jedis);
    	}
    	
    }
    /**
     * 获取 SET所有成员
     * @param key
     */
    public static Set<String> smembersSet(String key){
    	
    	Jedis jedis = null;
    	Set<String> set =null;
    	try {  
    		jedis = redisUtil.jedisPool.getResource(); 
    		set=jedis.smembers(key);
    		if(logger.isTraceEnabled()){
         	   jedis.incr(Constants.REDIS_SEARCH_COUNT);
         	   logger.trace("查询redis次数：{}",jedis.get(Constants.REDIS_SEARCH_COUNT));
            }
    	} catch (Exception e) { 
    		if(logger.isDebugEnabled()){
				logger.debug("Redis获取Set类型出错,key为{}",key,e);
    		}
    		e.printStackTrace();
    	}finally{
    		returnResource(jedis);
    	}
    	return set;
    }
    /**
     * 获取 SET中元素个数
     * @param key
     */
    public static long scardSet(String key){
    	
    	Jedis jedis = null;
    	long count =0;
    	try {  
    		jedis = redisUtil.jedisPool.getResource(); 
    		count=jedis.scard(key);
    	} catch (Exception e) { 
    		if(logger.isDebugEnabled()){
    			logger.debug("Redis获取Set类型元素个数出错,key为{}",key,e);
    		}
    		e.printStackTrace();
    	}finally{
    		returnResource(jedis);
    	}
    	return count;
    }
    /**
     * 设置 ZSET
     * @param key
     * @param value
     */
    public static void zaddSet(String key ,int score,String value){
    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           value=value==null?"":value;
           jedis.zadd(key, score, value);
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
	        	if(existsKey(key)){
	        		logger.debug("Redis修改sortedSet类型出错,key为{},score为{},value为{}",new Object[]{key,score,value},e);
	        	}else{
	        		logger.debug("Redis新增sortedSet类型出错,key为{},score为{},value为{}",new Object[]{key,score,value},e);
	        	}
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
        
    }
    
    /**
     * 查询 ZSET指定范围的集合
     * @param key
     * @param min
     * @param max
     */
    public static Set<String> zrangeSet(String key ,int min,int max){
    	
        Jedis jedis = null;
        Set<String> set=null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           set=jedis.zrange(key, min, max);
           if(logger.isTraceEnabled()){
        	   jedis.incr(Constants.REDIS_SEARCH_COUNT);
        	   logger.trace("查询redis次数：{}",jedis.get(Constants.REDIS_SEARCH_COUNT));
           }
        } catch (Exception e) { 
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
    	return set;
    }
    
    /**
     * 设置 过期时间
     * @param key
     * @param seconds 以秒为单位
     */
    public static void expire(String key ,int seconds){
    	
        Jedis jedis = null;
        try {
           jedis = redisUtil.jedisPool.getResource(); 
           jedis.expire(key, seconds);
        } catch (Exception e) { 
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
    }
    
    public static void setMap(String key, Map<String,String> map){
    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           jedis.hmset(key, map);
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
	        	if(existsKey(key)){
	    			logger.debug("Redis修改Map类型出错,key为{},value原值为{}",new Object[]{key,jedis.get(key)},e);
	    			logger.debug("Redis修改Map类型出错,key为{},value修改为{}",new Object[]{key,map},e);
	    		}else{
	    			logger.debug("Redis新增Map类型出错,key为{},value为{}",new Object[]{key,map},e);
	    		}
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
    }
    
    public static Map<String,String> getMap(String key){
    	
    	Map<String,String> result=new HashMap<String,String>();
    	
        Jedis jedis = null;
        try {  
           jedis = redisUtil.jedisPool.getResource(); 
           result =jedis.hgetAll(key);
           if(logger.isTraceEnabled()){
        	   jedis.incr(Constants.REDIS_SEARCH_COUNT);
        	   logger.trace("查询redis次数：{}",jedis.get(Constants.REDIS_SEARCH_COUNT));
           }
        } catch (Exception e) { 
        	if(logger.isDebugEnabled()){
        		logger.debug("Redis获取Map类型出错,key为{}",key,e);
        	}
        	e.printStackTrace();
        }finally{
            returnResource(jedis);
        }
        
    	return result;
    }
    
    /**
     * 清空redis
     */
    public static void flushDB(){
    	
    	Jedis jedis = null;
    	try {  
    		jedis = redisUtil.jedisPool.getResource(); 
    		jedis.flushDB();
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}finally{
    		returnResource(jedis);
    	}
    }
	     
}
