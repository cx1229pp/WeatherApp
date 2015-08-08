package com.weather.app.tool;

/**
 * 回调函数类
 * @author ldn
 *
 */
public interface HttpCallbackListener {
	/**
	 * 当请求完成时回调该函数获得返回值
	 * @param response
	 */
	public void onFinash(String response);
	
	/**
	 * 当请求报错时，回调该函数获取错误信息
	 * @param e
	 */
	public void onError(Throwable e);
}
