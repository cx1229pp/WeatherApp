package com.weather.app.tool;

/**
 * �ص�������
 * @author ldn
 *
 */
public interface HttpCallbackListener {
	/**
	 * ���������ʱ�ص��ú�����÷���ֵ
	 * @param response
	 */
	public void onFinash(String response);
	
	/**
	 * �����󱨴�ʱ���ص��ú�����ȡ������Ϣ
	 * @param e
	 */
	public void onError(Throwable e);
}
