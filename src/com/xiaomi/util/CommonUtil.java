package com.xiaomi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonUtil {
	/**
	 * ʵ����תJSON����
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static JSONObject convertBeanToJSONObject(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		// ��ȡclass����
		Class<? extends Object> cls = obj.getClass();
		// ��ȡ�����б�
		Field[] fields = cls.getDeclaredFields();
		// ���ð�ȫ��
		Field.setAccessible(fields, true);
		JSONObject jo = new JSONObject();
		// ���������б�
		for (Field f : fields) {
			// �ж������Ƿ�Ϊ�ջ����ǳ���
			if (f.get(obj) != null && !Modifier.isFinal(f.getModifiers())) {
				jo.put(f.getName(), f.get(obj).toString());
			}
		}
		return jo;
	}

	/**
	 * list����תJSONArray����
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static JSONArray convertListToJSONArray(List list) throws IllegalArgumentException, IllegalAccessException {
		JSONArray ja = new JSONArray();
		for (Object o : list) {
			ja.add(convertBeanToJSONObject(o));
		}
		return ja;
	}

	/** ��ȡIP��ַ */
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// ��������ȡ�������õ�IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// ����ͨ�����������������һ��IPΪ�ͻ�����ʵIP,���IP����','�ָ�
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
