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
		// �̷߳�ʽ�������Ӷ���Ҫ��������
		return sf.getCurrentSession();
	}
	
}
