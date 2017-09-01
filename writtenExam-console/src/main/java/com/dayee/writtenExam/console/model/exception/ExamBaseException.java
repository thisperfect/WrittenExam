package com.dayee.writtenExam.console.model.exception;

/**
 * Created by Administrator on 2017/8/7.
 */
public class ExamBaseException extends RuntimeException{

    protected String code;			//异常编码
    protected String reason;		//异常原因
    protected String solution;		//解决办法

    public ExamBaseException(String msg, Throwable e) {

        super(msg, e);
    }

    public ExamBaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ExamBaseException(String code, String message, String reason, String solution) {
        super(message);
        this.code = code;
        this.reason = reason;
        this.solution = solution;
    }

    public ExamBaseException(String code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public ExamBaseException(String code, String message, String reason, String solution, Throwable t) {
        super(message, t);
        this.code = code;
        this.reason = reason;
        this.solution = solution;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getSolution() {
        return solution;
    }
}
