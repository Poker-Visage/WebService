package com.tedu.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpResponse {

	private int statusCode=200;
	private String statusReason="OK";
	
	private Map<String, String>headers=new HashMap<String, String>();
	private File entity;
	private Socket socket;
	private OutputStream out;
	
public HttpResponse(Socket socket) {
	try {
	   this.socket=socket;
	   this.out=socket.getOutputStream();
	} catch (Exception e) {
	
	}
}

  public void flush() {
	  sendStatusLine();
	  sendHeader();
	  sendContent();
  }
  private void sendStatusLine() {
	try {
				String line="HTTP/1.1"+" "+statusCode+" "+statusReason;
				println(line);
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
private void sendHeader() {
	try {
		for(Entry<String, String>header:headers.entrySet()) {
			String name=header.getKey();
			String value=header.getValue();
			println(name+": "+value);
		}
		
	println("");
	} catch (Exception e) {
	e.printStackTrace();
	}  
	
  }
private void sendContent() {
	  try (FileInputStream fis
			  =new FileInputStream(entity);
			  ){
		  byte[]data=new byte[1024*10];
			int len=-1;
			while((len=fis.read(data))!=-1) {
				out.write(data,0,len);
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

private void println(String line) {
	try {
		out.write(line.getBytes("ISO8859-1"));
		out.write(13);
		out.write(10);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	public File getEntity() {
		return entity;
	}

	public void setEntity(File entity) {
		if(entity.getName().matches(".+\\.[a-zA-Z0-9]+")) {
		String ext=entity.getName().split("\\.")[1];
		headers.put("Content-Type", HttpContext.getMimeType(ext));
		}
		headers.put("Content-Length",entity.length()+"");
		this.entity = entity;
	}
	
	
	public void putHeader(String name,String value) {
		this.headers.put(name, value);
	}
	public String getHeader(String name) {
		return this.headers.get(name);
	}
	
	public int getStatusCode() {   
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		this.statusReason=HttpContext.getStatusReason(statusCode);
	}
	
}
