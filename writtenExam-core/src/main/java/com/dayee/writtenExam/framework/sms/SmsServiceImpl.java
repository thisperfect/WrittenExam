
package com.dayee.writtenExam.framework.sms;

import com.dayee.writtenExam.framework.constant.Constants;
import com.dayee.writtenExam.framework.util.PropertiesEnum;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory
                                               .getLogger(SmsServiceImpl.class);

    @Override
    @IdeaJdbcTx
    public String send(String phone, String content) {

        logger.info("准备发送短信给:{},内容：{}", phone, content);
        String config = PropertiesEnum.RESUME_CONFIG.getProperty(Constants.DEBUG_SENDSMS);
        if (config != null && config.equals("false")) {
            logger.info("开发模式不发送短信(yuncai.debug.sendSms=false)");
            return String.valueOf(C123SendSms.SUCCESS);
        }
        String result = C123SendSms.send(null, phone, content);
        logger.info("发送短信结果:{}",result);
        return result;
    }

}
