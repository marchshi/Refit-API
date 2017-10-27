package com.smq.mode2.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.smq.mode2.bean.CatTableBean;

public class CatTableDao {
	
	public List<CatTableBean> getTable(String path){
		List<CatTableBean> list = new ArrayList();
		Document doc = null;
		try {
			doc = new SAXReader().read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if(doc!=null){
			List<Element> catOne = doc.getRootElement().elements("cat-one");
			for(Element e1:catOne){
				CatTableBean bean1 = new CatTableBean();
				bean1.setLevel("1");
				bean1.setId(e1.attributeValue("id"));
				bean1.setName(e1.attributeValue("value"));
				list.add(bean1);
				List<Element> catTwo = e1.elements("cat-two");
				for(Element e2:catTwo){
					CatTableBean bean2 = new CatTableBean();
					bean2.setLevel("2");
					bean2.setParentId(e1.attributeValue("id"));
					bean2.setId(e2.attributeValue("id"));
					bean2.setName(e2.attributeValue("value"));
					list.add(bean2);
				}
			}
		}
		return list;
	}
	
}
