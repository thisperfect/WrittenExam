package com.dayee.writtenExam.console.logic.writtenExamUser;

import com.dayee.writtenExam.console.dao.writtenExamUser.WrittenExamUserDao;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.writtenExam.WrittenExamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/7.
 */

@Component
public class WrittenExamUserLogic {

    @Autowired
    private WrittenExamUserDao writtenExamUserDao;

    public String login(String userName, String passWord){
        WrittenExamUser user = writtenExamUserDao.findWrittenExamUserByUserNameAndPassWord(userName, passWord);
        if(user == null){
            return "账号密码错误";
        }else if(user.getEndTime().getTime() < new Date().getTime()){
            return "账号已过期,请续费";
        }else{
            return "success";
        }
    }

    public String saveWrittenExamUser(WrittenExamUser user){
        if(user != null && StringUtils.isBlank(user.getId())){
            WrittenExamUser existUser = writtenExamUserDao.findWrittenExamUserByUserName(user.getUserName());
            if(existUser != null){
                return "用户名已存在";
            }
            writtenExamUserDao.saveWrittenExamUser(user);
            return "SUCCESS";
        } else{
            writtenExamUserDao.updateWrittenExamUser(user);
            return "SUCCESS";
        }

    }

    public String renewalUser(Date newEndDate, String userId) {
        WrittenExamUser user = writtenExamUserDao.findWrittenExamUserByUserId(userId);
        if(user == null){
            return "该用户不存在";
        }else{
            writtenExamUserDao.UpdateEndDateByUserId(newEndDate, userId);
             return "SUCCESS";
        }
    }

    public String delWrittenExamUser(String id) {
        if(StringUtils.isBlank(id)){
            return "用户不存在";
        }
        writtenExamUserDao.delWrittenExamUser(id);
        return "SUCCESS";
    }
}
