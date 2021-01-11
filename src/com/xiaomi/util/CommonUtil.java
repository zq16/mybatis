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
	 * 实体类转JSON对象
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static JSONObject convertBeanToJSONObject(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		// 获取class对象
		Class<? extends Object> cls = obj.getClass();
		// 获取属性列表
		Field[] fields = cls.getDeclaredFields();
		// 设置安全性
		Field.setAccessible(fields, true);
		JSONObject jo = new JSONObject();
		// 遍历属性列表
		for (Field f : fields) {
			// 判断属性是否为空或者是常量
			if (f.get(obj) != null && !Modifier.isFinal(f.getModifiers())) {
				jo.put(f.getName(), f.get(obj).toString());
			}
		}
		return jo;
	}

	/**
	 * list集合转JSONArray对象
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

	/** 获取IP地址 */
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
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
