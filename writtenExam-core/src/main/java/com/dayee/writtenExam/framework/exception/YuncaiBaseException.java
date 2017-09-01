/*
 * IDEADP是IDEAMOMENT Develop Platform的缩写。
 * 由北京创想时刻信息技术有限公司研发。项目提供各种常见问题的最佳实践和解决方案，用于提高各产品和项目的开发效率。
 * 
 * Copyright @ www.ideamoment.com
 * All right reserved.
 */
package com.dayee.writtenExam.framework.exception;

/**
 * @author Chinakite Zhang
 *
 */
public class YuncaiBaseException extends RuntimeException {
	
	private static final long serialVersionUID = -5585985001355363261L;
	
	protected String code;			//异常编码
	protected String reason;		//异常原因
	protected String solution;		//解决办法
	
    public YuncaiBaseException(String msg, Throwable e) {

        super(msg, e);
    }
	
	public YuncaiBaseException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public YuncaiBaseException(String code, String message, String reason, String solution) {
		super(message);
		this.code = code;
		this.reason = reason;
		this.solution = solution;
	}
	
	public YuncaiBaseException(String code, String message, Throwable t) {
		super(message, t);
		this.code = code;
	}
	
	public YuncaiBaseException(String code, String message, String reason, String solution, Throwable t) {
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
