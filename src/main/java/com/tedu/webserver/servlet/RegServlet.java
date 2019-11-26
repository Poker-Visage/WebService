package com.tedu.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

public class RegServlet extends HttpServlet{
public void  service(HttpRequest request,HttpResponse response) {
	String username=request.getParameter("username");
	String passwork=request.getParameter("password");
	String nickname=request.getParameter("nickname");
	int age=Integer.parseInt(request.getParameter("age"));
	try {
		RandomAccessFile raf
		    =new RandomAccessFile("user.dat", "rw");
		raf.seek(raf.length());
		byte[]data=username.getBytes("utf-8");
		data=Arrays.copyOf(data, 32);
		raf.write(data);
		data=passwork.getBytes("utf-8");
		data=Arrays.copyOf(data, 32);
		raf.write(data);
		data=nickname.getBytes("utf-8");
		data=Arrays.copyOf(data, 32);
	   raf.write(data);
	   
	   raf.writeInt(age);
	   System.out.println("注册完毕！");
	   forward("/myweb/reg_success.html", request, response);
	   
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	System.out.println("username:"+username);
	System.out.println("password:"+passwork);
	System.out.println("nickname:"+nickname);
	System.out.println("age:"+age);
}

}
