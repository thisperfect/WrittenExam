package com.dayee.writtenExam.console.service.sms;

import com.dayee.writtenExam.console.logic.sms.C123SendSms;
import com.dayee.writtenExam.rpcInterface.SmsInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/8/7.
 */
public class SmsServiceImpl implements SmsInterface {

    private static final Logger logger = LoggerFactory
            .getLogger(SmsServiceImpl.class);

    @Override
    public String sendSms(String phone, String content) {

        String result = C123SendSms.send(null, phone, content);
        logger.info("发送短信结果:{}",result);
        return result;
    }
}
