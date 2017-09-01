package com.dayee.writtenExam.regex;

import java.text.MessageFormat;

import com.dayee.writtenExam.framework.util.StringUtils;

/**
 * Created by yq.song on 2016/10/9.
 */
public class RegexUtils {
	
	/**匹配指定字符串中的一个或几个**/
	private static final String REGEX_MATCH_ANYSTR=".*?({0}).*";
	
	/**匹配手机号**/
	private static final String REGEX_PHONE="^1[34578]\\d{9}$";
	
	/**匹配正整数**/
	private static final String REGEX_POSITIVE_INTEGER="^[1-9][0-9]*$";
	
	private static String covertMatchStr(String regex,String... params){
		
		if(StringUtils.isBlank(regex)){
			return null;
		}
		
		return MessageFormat.format(regex,params);
	}
	
	/**
	 * 获取匹配指定的任意字符的正则表达式
	 * @param params
	 * @return
	 */
	public static String getMatchAnyStrRegex(String... params){
		
		if(params==null || params.length==0){
			return null;
		}
		
		StringBuffer strs=new StringBuffer();
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				
				strs.append(params[i].replaceAll(",","|"));
				if(i<params.length-1){
					strs.append("|");
				}
			}
		}
		return covertMatchStr(REGEX_MATCH_ANYSTR,strs.toString());
	}
	
	/**
	 * 获取匹配手机号的正则表达式
	 * @return
	 */
	public static String getPhoneRegex(){
		
		return REGEX_PHONE;
	}
	
	/**
	 * 获取匹配正整数的正则表达式
	 * @return
	 */
	public static String getPositiveIntegerRegex(){
		
		return REGEX_POSITIVE_INTEGER;
	}
	
	public static void main(String[] args) {
		System.out.println("13934233333".matches(null));
		System.out.println(covertMatchStr(REGEX_PHONE));
		System.out.println("5".matches(getPositiveIntegerRegex()));
	}
	
}
