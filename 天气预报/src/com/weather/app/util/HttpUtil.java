package com.weather.app.util;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * http操作工具类
 * @author chenxue
 *
 */
public class HttpUtil {
	private static final int readTime = 3000;
	private static final int connectTime = 3000;
	
	/**
	 * http请求，结果返回字符串
	 * @param urlStr 请求URL
	 * @return  String
	 */
	public static String requestString(String urlStr){
		String result = "";
		
		InputStream is = requestStream(urlStr);
		try {
			result = IOUtils.toString(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * http请求，结果返回byte数组
	 * @param urlStr 请求URL
	 * @return byte[]
	 */
	public static byte[] requestByte(String urlStr){
		byte[] result = null;
		
		InputStream is = requestStream(urlStr);
		try {
			result = IOUtils.toByteArray(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * http请求，结果返回InputStream
	 * @param urlStr 请求URL
	 * @return InputStream
	 */
	public static InputStream requestStream(String urlStr){
		InputStream result = null;
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(connectTime);
			con.setReadTimeout(readTime);
			
			int resultCode = con.getResponseCode();
			if(resultCode == HttpStatus.SC_OK){
				result = con.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}