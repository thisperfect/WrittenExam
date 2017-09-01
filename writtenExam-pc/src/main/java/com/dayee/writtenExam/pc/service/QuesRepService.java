package com.dayee.writtenExam.pc.service;


import com.dayee.writtenExam.rpcInterface.QuesRepInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangchen on 2017/7/7.
 */
@Service
public class QuesRepService {

    /*调用dubbo接口*/
    @Autowired
    private QuesRepInterface quesRepInterface;

    public String testDubbo(){
        return quesRepInterface.showStr("test");
    }
}
