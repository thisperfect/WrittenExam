package com.dayee.writtenExam.quesRep.service;

import com.dayee.writtenExam.rpcInterface.QuesRepInterface;

/**
 * Created by wangchen on 2017/6/28.
 */
public class QuesRepInterfaceImpl implements QuesRepInterface {

    private String repStr = "rep";
    @Override
    public String showStr(String s) {
        return s+repStr;
    }
}
