package com.dayee.writtenExam.rpcInterface;

import com.dayee.writtenExam.model.writtenExam.WrittenExamUser;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/7.
 */
public interface WrittenExamUserInterface {

        String login(String userName, String password);

        String saveWrittenExamUser(WrittenExamUser user);

        String renewalUser(Date newEndDate, String userId);

        String delWrittenExamUser(String id);


}
