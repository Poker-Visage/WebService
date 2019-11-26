package com.tedu.webserver.http;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HttpContext {
	
   private static Map<String, String> MIME_MAPPING=new HashMap<String, String>();
   private static Map<Integer, String>STATUS_CODE_REASON_MAPPING=new HashMap<Integer, String>();
   static {
	   initMimeMapping();
	   initStatusCodeReasonMapping();
}
   
   private static void initStatusCodeReasonMapping() {
	   STATUS_CODE_REASON_MAPPING.put(200, "OK");
	   STATUS_CODE_REASON_MAPPING.put(302, "Moved Temporarily");
	   STATUS_CODE_REASON_MAPPING.put(404, "Not found");
	   STATUS_CODE_REASON_MAPPING.put(500, "Internal Server Error");
   }
   private static void initMimeMapping() {
	   try {
		 Document doc=new SAXReader().read("conf/web.xml");
		 Element ele=doc.getRootElement();
		 List<Element>list=ele.elements("mime-mapping");
		 for(Element elem:list) {
			 MIME_MAPPING.put(elem.elementText("extension"), elem.elementText("mime-type"));
		 }
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
 
   public static String getMimeType(String name) {
	   return MIME_MAPPING.get(name);
   }
   public static String getStatusReason(int code) {
	   return STATUS_CODE_REASON_MAPPING.get(code);
   }
   public static void main(String[] args) {
	String reason=HttpContext.getStatusReason(404);
	System.out.println(reason);
	
}
   
}
