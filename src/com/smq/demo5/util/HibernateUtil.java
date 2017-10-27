package com.smq.demo5.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static SessionFactory sf ;
	
	static{
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	public static Session getSession(){
		// 线程方式创建连接对象，要进行配置
		return sf.getCurrentSession();
	}
	
}
