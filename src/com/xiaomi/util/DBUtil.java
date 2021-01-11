package com.xiaomi.util;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUtil {
	private static SqlSessionFactory factory = null;
	private DBUtil() {}
	
	static {
		getFactory();
	}
	
	public static SqlSessionFactory getFactory() {
		if(factory == null) {
			synchronized (DBUtil.class) {
				try {
					factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("SqlMapConfig.xml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return factory;
	}
	
	public static SqlSession getSession() {
		return factory.openSession();
	}
}
