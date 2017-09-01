
package com.dayee.writtenExam.framework.config.dao;

import java.util.Date;
import java.util.List;

import com.dayee.writtenExam.framework.core.dao.CommonDao;
import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.model.config.ConfigRuleItem;
import org.springframework.stereotype.Component;

/**
 * @author cooltain
 */
@Component
public class ConfigRuleDao extends CommonDao {

    @SuppressWarnings("unchecked")
    public ConfigRuleItem getRuleItem(String corpCode,String key) {

        String sql = "select * from T_CONFIG_RULE_ITEM where C_CORPCODE=:corpCode and C_KEY=:key";
        ConfigRuleItem item = (ConfigRuleItem) WrittenJdbc.query(sql)
                .setParameter("corpCode", corpCode).setParameter("key", key)
                .uniqueTo(ConfigRuleItem.class);
        return item;
    }
    
    @SuppressWarnings("unchecked")
    public ConfigRuleItem getCorpCodeItem(String value,String key) {

        String sql = "select * from T_CONFIG_RULE_ITEM where C_VALUE=:value and C_KEY=:key";
        ConfigRuleItem item = (ConfigRuleItem) WrittenJdbc.query(sql)
                .setParameter("value", value).setParameter("key", key)
                .uniqueTo(ConfigRuleItem.class);
        return item;
    }

    public void setRule(String corpCode,String key, String newValue) {

        String corpUserId = UserContext.getCurrentContext().getUser()
                .getUserId();
        ConfigRuleItem item = getRuleItem(corpCode, key);

        if (item == null) {
            // 已经有规则
            if (newValue != null) {
                item = new ConfigRuleItem();
                item.setCorpCode(corpCode);
                item.setKey(key);
                item.setValue(newValue);
                Date now = new Date();
                item.setCreator(corpUserId);
                item.setCreateTime(now);
                item.setModifier(corpUserId);
                item.setModifyTime(now);
                WrittenJdbc.save(item);
            }
        } else {
            // 还没有规则
            if (newValue == null) {
                if (item.getValue() == null) {
                    return; // do nothing. 两者同为空，维持原样
                }
            } else {
                if (newValue.equals(item.getValue())) {
                    return; // do nothing. 两者值相同，维持原样
                }
            }
            item.setValue(newValue);
            item.setModifier(corpUserId);
            Date now = new Date();
            item.setModifyTime(now);
            WrittenJdbc.updateCorp(item);
        }
    }

    private static final String SQL_SELECT_MAIL_CONFIG = "select * from T_CONFIG_RULE_ITEM where C_CORPCODE = :corpCode and C_KEY in('mailToken','isEws','mailAddress','mailUserName','mailPassword','smtpServer','smtpPort','sslStatus','tryTime','sendMode','showSendName') ";

    @SuppressWarnings("unchecked")
    public List<ConfigRuleItem> getSendMailRuleList() {

        String corpCode = UserContext.getCurrentContext().getCorpCode();
        List<ConfigRuleItem> itemList = WrittenJdbc
                .query(SQL_SELECT_MAIL_CONFIG)
                .setParameter("corpCode", corpCode)
                .listTo(ConfigRuleItem.class);
        return itemList;
    }
}
