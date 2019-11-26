package com.tedu.webserver.servlet;
import java.io.File;
import java.io.RandomAccessFile;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

public class LoginServlet extends HttpServlet{
public void service(HttpRequest request,HttpResponse response) {
	String username=request.getParameter("username");
	String passwork=request.getParameter("password");
	try {
		RandomAccessFile rea=new RandomAccessFile("user.dat", "r");
		boolean have=false;
		for(int i=0;i<rea.length()/100;i++) {
			rea.seek(i*100);
			byte[]data=new byte[32];
			rea.read(data);
			String name=new String(data,"utf-8").trim();
			if(name.equals(username)) {
				rea.read(data);
				String pw=new String(data,"utf-8").trim();
				if(pw.equals(passwork)) {
					forward("/myweb/login_success.html", request, response);
					have=true;
				 }
				break;
			}
		}
		if(!have) {
			forward("/myweb/login_fail.html", request, response);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
