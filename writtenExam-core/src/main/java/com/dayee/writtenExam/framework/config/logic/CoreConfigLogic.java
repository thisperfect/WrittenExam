package com.dayee.writtenExam.framework.config.logic;

import java.util.List;

import com.dayee.writtenExam.framework.config.dao.ConfigRuleDao;
import com.dayee.writtenExam.framework.exception.YuncaiBaseException;
import com.dayee.writtenExam.framework.util.CollectionUtils;
import com.dayee.writtenExam.framework.util.PropertyUtils;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.config.ConfigRuleItem;
import com.dayee.writtenExam.model.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoreConfigLogic {

    @Autowired
    private ConfigRuleDao configRuleDao;
    
    public MailConfig getSendMailConfig() {

        List<ConfigRuleItem> itemList = configRuleDao.getSendMailRuleList();
        MailConfig mailConfig = new MailConfig();
        boolean hasValue = false;
        if (CollectionUtils.isNotEmpty(itemList)) {
            for (ConfigRuleItem ruleItem : itemList) {
                String key = ruleItem.getKey();
                String value = ruleItem.getValue();
                if (StringUtils.hasLength(value)) {
                    try {
                        if (PropertyUtils.isWriteable(mailConfig, key)) {
                            hasValue = true;
                            PropertyUtils.setProperty(mailConfig, key, value);
                        }
                    } catch (Exception e) {
                        throw new YuncaiBaseException("获取发送邮件配置异常", e);
                    }
                }
            }
        }
        if (hasValue) {
            return mailConfig;
        } else {
            return null;
        }

    }

}
