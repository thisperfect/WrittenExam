package com.dayee.writtenExam.console.dao.writtenExamUser;

import com.dayee.writtenExam.framework.ideajdbc.baseDao.BaseDao;
import com.dayee.writtenExam.model.writtenExam.WrittenExamUser;
import com.ideamoment.ideajdbc.IdeaJdbc;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/7.
 */
@Component
public class WrittenExamUserDao extends BaseDao{

    public WrittenExamUser saveWrittenExamUser(WrittenExamUser user){
        return (WrittenExamUser) IdeaJdbc.save(user);
    }


    public WrittenExamUser findWrittenExamUserByUserNameAndPassWord(String userName, String passWord){
        String sql = "SELECT * FROM T_WRITTENEXAM_USER WHERE F_USER_NAME = :userName AND F_PASSWORD = :passWord LIMIT 1";
        return (WrittenExamUser) IdeaJdbc.query(sql).setParameter("userName",userName).setParameter("passWord",passWord).uniqueTo(WrittenExamUser.class);

    }

    public WrittenExamUser findWrittenExamUserByUserName(String userName) {
        String sql = "SELECT * FROM T_WRITTENEXAM_USER WHERE F_USER_NAME = :userName LIMIT 1";
        return (WrittenExamUser) IdeaJdbc.query(sql).setParameter("userName",userName).uniqueTo(WrittenExamUser.class);
    }


    public WrittenExamUser findWrittenExamUserByUserId(String userId) {
        String sql = "SELECT * FROM T_WRITTENEXAM_USER WHERE F_ID = :userId LIMIT 1";
        return (WrittenExamUser) IdeaJdbc.query(sql).setParameter("userId",userId).uniqueTo(WrittenExamUser.class);
    }

    public void UpdateEndDateByUserId(Date newEndDate, String userId) {
        String sql = "UPDATE T_WRITTENEXAM_USER SET F_END_TIME = :newEndDate WHERE F_ID = :userId";
        IdeaJdbc.sql(sql).setParameter("newEndDate",newEndDate).setParameter("userId",userId).execute();
    }

    public void updateWrittenExamUser(WrittenExamUser user) {
        IdeaJdbc.update(user);
    }

    public void delWrittenExamUser(String id) {
        String sql = "DELETE FROM T_WRITTENEXAM_USER WHERE F_ID = :id";
        IdeaJdbc.sql(sql).setParameter("id",id).execute();
    }
}
