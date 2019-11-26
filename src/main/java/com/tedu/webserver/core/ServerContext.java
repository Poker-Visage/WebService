package com.tedu.webserver.core;
import java.io.File;
/*
 * Servelet映射信息
 * key：url
 * value：Servelet名字
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerContext {
	private static Map<String,String>SERVLET_MAPPING=
			new HashMap<String,String>();
	static {
		initServletMapping();
	}
	
	private static void initServletMapping() {
		try {
			 Element doc=new SAXReader().read(new File("conf/servlets.xml")).getRootElement();
			 List<Element>list=doc.elements("servlet");
			 for(Element elem:list) {
				 SERVLET_MAPPING.put(elem.elementText("url"), elem.elementText("className"));
			 }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean hasServlet(String url) {
		return SERVLET_MAPPING.containsKey(url);
	}
	
	public static String getServletByUrl(String url) {
		return SERVLET_MAPPING.get(url);
	}
	
}
