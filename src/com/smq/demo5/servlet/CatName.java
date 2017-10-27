package com.smq.demo5.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.util.BaseJson;
import com.smq.demo5.util.HibernateUtil;
import com.smq.demo5.util.r;

/**
 * 返回类目名称表 
 */
public class CatName extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSession();
		List<CatNameBean> list = session.createQuery("from CatNameBean").list();
		if(list.isEmpty()){
			Document doc = null;
			String path = request.getRealPath("/") + "category1.xml";
			try {
				doc = new SAXReader().read(new File(path));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			if(doc!=null){
				List<Element> catOne = doc.getRootElement().elements("cat-one");
				for(Element e1:catOne){
					CatNameBean bean1 = new CatNameBean();
					int high = Integer.parseInt(e1.attributeValue("id")) * 100;
					bean1.setNameId(high);
					bean1.setParentName(e1.attributeValue("value"));
					bean1.setChildName(e1.attributeValue("value"));
					list.add(bean1);
					session.merge(bean1);
					List<Element> catTwo = e1.elements("cat-two");
					for(Element e2:catTwo){
						CatNameBean bean2 = new CatNameBean();
						int low = Integer.parseInt(e2.attributeValue("id"));
						bean2.setNameId(high+low);
						bean2.setChildName(e2.attributeValue("value"));
						bean2.setParentName(e1.attributeValue("value"));
						list.add(bean2);
						session.merge(bean2);
					}
				}
			}
		}
		r.success(response, new Gson().toJson(list));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse responce)
			throws ServletException, IOException {
		
	}
}
