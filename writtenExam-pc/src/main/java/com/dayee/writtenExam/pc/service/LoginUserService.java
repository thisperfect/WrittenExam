package com.dayee.writtenExam.pc.service;

import com.dayee.writtenExam.pc.dao.LoginUserDao;
import com.dayee.writtenExam.pc.model.LoginUser;
import com.ideamoment.ideajdbc.spring.IdeaJdbcTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangchen on 2017/7/8.
 */
@Service
public class LoginUserService {

    @Autowired
    private LoginUserDao loginUserDao;

    @IdeaJdbcTx(readOnly = true)
    public List<LoginUser> getUsers(){
        return loginUserDao.getLoginUser();
    }

}
