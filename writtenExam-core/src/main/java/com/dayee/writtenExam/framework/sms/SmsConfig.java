package com.dayee.writtenExam.framework.sms;

import com.dayee.writtenExam.framework.util.StringUtils;


public class SmsConfig {

    private String              c123Account;

    private String              interfaceKey;

    private String              c123Url;

    private String              channelGroup;

    
    public String getC123Account() {
    
        return c123Account;
    }

    
    public void setC123Account(String c123Account) {
    
        this.c123Account = c123Account;
    }

    
    public String getInterfaceKey() {
    
        return interfaceKey;
    }

    
    public void setInterfaceKey(String interfaceKey) {
    
        this.interfaceKey = interfaceKey;
    }

    
    public String getC123Url() {
    
        return c123Url;
    }

    
    public void setC123Url(String c123Url) {
    
        this.c123Url = c123Url;
    }

    
    public String getChannelGroup() {
    
        return channelGroup;
    }

    
    public void setChannelGroup(String channelGroup) {
    
        this.channelGroup = channelGroup;
    }
    
    
    public boolean canSend() {

        return StringUtils.hasLength(c123Account) && StringUtils
                       .hasLength(interfaceKey)
               && StringUtils.hasLength(c123Url)
               && StringUtils.hasLength(channelGroup);
         
    }
    
    
    
}
