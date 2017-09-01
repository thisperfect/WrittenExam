package com.dayee.writtenExam.framework.sms;


public interface SmsService {
    
    /**
     * 发送短信
     * @param phone
     * @param content
     * @return
     */
    public   String send(String phone, String content);

}
