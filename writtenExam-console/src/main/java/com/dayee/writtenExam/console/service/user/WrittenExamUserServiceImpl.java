package com.dayee.writtenExam.console.service.user;

import com.dayee.writtenExam.console.logic.writtenExamUser.WrittenExamUserLogic;
import com.dayee.writtenExam.model.writtenExam.WrittenExamUser;
import com.dayee.writtenExam.rpcInterface.WrittenExamUserInterface;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/7.
 */
@Service
public class WrittenExamUserServiceImpl  implements WrittenExamUserInterface {

    @Autowired
    private WrittenExamUserLogic    writtenExamUserLogic;

    @Override
    @IdeaJdbcTx(readOnly = true)
    public String login(String userName, String password) {

        return writtenExamUserLogic.login(userName, password);
    }

    @Override
    @IdeaJdbcTx
    public String saveWrittenExamUser(WrittenExamUser user) {
        return writtenExamUserLogic.saveWrittenExamUser(user);
    }

    @Override
    @IdeaJdbcTx
    public String renewalUser(Date newEndDate, String userId) {
        return writtenExamUserLogic.renewalUser(newEndDate, userId);
    }

    @Override
    @IdeaJdbcTx
    public String delWrittenExamUser(String id) {
        return writtenExamUserLogic.delWrittenExamUser(id);
    }
}
