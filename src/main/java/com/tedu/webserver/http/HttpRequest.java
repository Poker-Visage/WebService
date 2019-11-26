package com.tedu.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;//Decoder 译码器
import java.util.HashMap;
import java.util.Map;

import com.tedu.webserver.core.EmptyRequestException;

public class HttpRequest {
	private Socket socket;
	private InputStream in;
	private String method;
	private String url;
	private String requestURI;
	private String queryString;
	private String protocol;
	
	private Map<String, String> headers=new HashMap<String, String>();//headers 域名
	private Map<String,String> parameters=new HashMap<String,String>(); 
	
	public HttpRequest(Socket socket) throws 
	EmptyRequestException {
	
	try {
		this.socket=socket;
		this.in=socket.getInputStream();
		parseRequestLine();
		parseHeaders();
		parseContent();
	}catch(EmptyRequestException e) {
		throw e;
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	private void parseContent() {
		
		try{
			if(this.headers.containsKey("Content-Length")) {
			int len=Integer.parseInt(
					this.headers.get("Content-Length"));
			byte[] data=new byte[len];
			in.read(data);
			String type=this.headers.get("Content-Type");
			if("application/x-www-form-urlencoded".equals(type)) {
				String str=new String(data,"ISO8859-1");
				str=URLDecoder.decode(str,"utf-8");
				parseParameters(str);
				System.out.println("正文中表单数据内容："+str);
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void parseHeaders() {
		
		System.out.println("开始解析消息头");
		while(true) {
			String lines=readLine();
			if("".equals(lines)) {
				break;
			}
			String[]datas=lines.split(":\\s");
			
			headers.put(datas[0], datas[1]);
			
		}
		System.out.println("headers:"+headers);
		System.out.println("解析完毕");
		
	}
	
		private void parseRequestLine() throws EmptyRequestException {
	String line=readLine().trim();
	String []data=line.split("\\s");
	if(data.length<3) {  
		throw new EmptyRequestException();
	}
	this.method=data[0];
	this.url=data[1];
	this.protocol=data[2];
	System.out.println(url);
	parseURL();
	}
	
	private void parseURL() {
		
		try {
			this.url=URLDecoder.decode(this.url, "utf-8");
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(this.url.contains("?")) {
			String[]data=this.url.split("\\?");
			this.requestURI=data[0];
			if(data.length>1) {
				this.queryString=data[1];
				parseParameters(this.queryString);
			}else {
				this.queryString="";
			}
		}else {
			this.requestURI=this.url;
		}
		System.out.println("进一步解析");
		System.out.println("requestURI:"+requestURI);
		System.out.println("queryString："+queryString);
		System.out.println("paparameters："+parameters);
		
		System.out.println("解析完毕");
		
	}
	
	public String getRequestURI() {
		return requestURI;
	}
	public String getQueryString() {
		return queryString;
	}
	
	private void parseParameters(String line) {
		String []data=line.split("&");
		for(String parameter:data) {
			String[]paraArr=parameter.split("=");
			if(paraArr.length>1) {
				parameters.put(paraArr[0], paraArr[1]);
			}else {
				parameters.put(paraArr[0], "");
			}
		}
	}
	
	private String readLine() {
		try{
			StringBuilder build=new StringBuilder();
			int d=1;
		char c1 = 'a',c2='a';
		while((d=in.read())!=-1) {
			c2=(char) d;
			if(c1==13&&c2==10) {
				break;
			}
			build.append(c2);
			c1=c2;
		}
		return build.toString().trim();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getProtocol() {
		return protocol;
	}
	
	public String getParameter(String name) {
		return this.parameters.get(name);
	}
}
