package com.cao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;

import sun.misc.BASE64Encoder;

public class Utils {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";

	public static final SimpleDateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");
	public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("######0.0");   
	
	public static String joinHql(String hql,String parameterName,Object parameter){
		if(parameter!=null){
			hql = hql+" and "+parameterName+"=:"+parameterName;
		}
		return hql;
	}
	
	public static void setParameter(Query query,String parameterName,Object parameter){
		if(parameter!=null){
			query.setParameter(parameterName, parameter);
		}
	}
	
    public static int getDateCount(int year,int month ,int date){
    	int total = 0;
        Calendar cal = Calendar.getInstance();    
        cal.set(Calendar.YEAR, year);    
        cal.set(Calendar.MONTH,  month - 1);    
        cal.set(Calendar.DATE, date);    
        while(cal.get(Calendar.YEAR) == year &&     
                cal.get(Calendar.MONTH) < month){    
            int day = cal.get(Calendar.DAY_OF_WEEK);    
            if(!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)){    
            	total++;
            }    
            cal.add(Calendar.DATE, 1);    
        }    
        return total;    
    }    
    
    public static String EncoderByMd5(String str) {
        String newstr = "";
		try {
	        //确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
	        BASE64Encoder base64en = new BASE64Encoder();
	        //加密后的字符串
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        return newstr;
    }
    
    public static String UncoderByMd5(String str){
    	String result=EncoderByMd5(str);
        return result;
    }
    
	public static void readAndWrite(String sourcePath, String targetPath) throws IOException {
		File source = new File(sourcePath);
		File target = new File(targetPath);
		if (!target.exists()) {
			target.createNewFile();
		}
		FileInputStream fis = new FileInputStream(source);
		FileOutputStream fos = new FileOutputStream(target);
		byte[] buffer = new byte[10240];
		int len = 0;
		while (-1 != (len = fis.read(buffer, 0, buffer.length))) {
			fos.write(buffer, 0, len);
		}
		fis.close();
		fos.close();
	}
	
	public static String proceseURL(String fileName){
		String url = Utils.class.getClassLoader().getResource("").toString();
		url = url.replace("WEB-INF/classes/", "files/").replace("file:/", "").trim();
		return url+fileName;
	}
	
	public static <T> List<T> arrayToList(T[] objs){
		List<T> list = new ArrayList<T>();
		for(int i =0;i<objs.length;i++){
			list.add(objs[i]);
		}
		return list;
	}
	
	public String randomString(){
		StringBuilder sb = new StringBuilder();
		return "";
	}

}
