package com.dayee.writtenExam.console.dao.email;

import com.dayee.writtenExam.framework.ideajdbc.baseDao.BaseDao;
import com.dayee.writtenExam.model.email.EmailConfig;
import com.ideamoment.ideajdbc.IdeaJdbc;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Component
public class EmailConfigDao extends BaseDao{

    public EmailConfig saveEmailConfig(EmailConfig emailConfig){

        return (EmailConfig) IdeaJdbc.save(emailConfig);
    }

    public int updateEmailConfig(EmailConfig emailConfig){
        return IdeaJdbc.update(emailConfig);
    }

    public EmailConfig getEmailConfig(){
        String sql = "SELECT * FROM T_EMAIL_CONF ORDER BY F_ADD_DATE DESC LIMIT 1";
        return (EmailConfig) IdeaJdbc.query(sql).uniqueTo(EmailConfig.class);
    }

}
