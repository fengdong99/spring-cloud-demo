package com.xuehai.web.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;


public class CommonUtil {
	
	//获取Uuid使用
	private static AtomicLong seq = new AtomicLong(1000);
	
	/**
	 * 获取18位UUID：yMMddHHmmssSSS + 四位递增数(原子性的)
	 * @return
	 */
	public static synchronized String getUuid(){
		StringBuffer bf = new StringBuffer();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		bf.append(sdFormat.format(new Date()).substring(3));
		long andIncrement = seq.getAndIncrement();
		if(andIncrement >= 9999){
			seq.set(1000);
		}
		return bf.append(andIncrement).toString();
	}
	//获取系统当前时间（年-月-日）
	public static String getSystemDate(){
		//取系统当前时间
		Date date = new Date();
		String systemDate = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		return systemDate;
	}

	//获取系统当前时间（年-月-日）
	public static String getSystemTime(){
		//取系统当前时间
		Date date = new Date();
		String systemDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);

		return systemDate;
	}
	
	/**
	 * 返回结果信息设置(service返回结果用)
	 * @param data 返回数据
	 * @param code 结果Code
	 * @param message 返回消息
	 * @return
	 */
	public static Map<String, Object> setResultInfo(Object data, Object code, String message) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("data", data);
		result.put("errorcode", code);
		result.put("message", message);
		return result;
	}
	




	

	
	
	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 20; i++) {
			System.out.println(getUuid());
		}
	}
}
