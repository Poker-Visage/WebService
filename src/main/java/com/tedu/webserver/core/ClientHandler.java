package com.tedu.webserver.core;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;
import com.tedu.webserver.servlet.HttpServlet;

public class ClientHandler implements Runnable{
private 	Socket socket;
public  ClientHandler(Socket socket) {
	this.socket=socket;
}
public void run() {
	System.out.println("一个客户端连接了！");
	try {
		/*
		 * 处理客户端请求流程
		 * 1：解析请求
		 * 2：处理请求
		 * 3：给予响应
		 */
		HttpRequest request=new HttpRequest(socket);
		HttpResponse response=new HttpResponse(socket);
		String url=request.getRequestURI();
		System.out.println("url"+url);
		if(ServerContext.hasServlet(url)) {
			
			String servletName=ServerContext.getServletByUrl(url);
			Class cls=Class.forName(servletName);
			HttpServlet servlet=(HttpServlet)cls.newInstance();
			servlet.service(request, response);
			
		}else {
			File file=new File("webapps"+url);
			if(file.exists()) {
				System.out.println("找到该文件！");
				response.setEntity(file);
			}else {
				response.setStatusCode(404);
				File notFoundPage=new File("webapps/sys/404.html");
				response.setEntity(notFoundPage);
			}
		}
		response.flush();
	} catch(EmptyRequestException e) {
		System.out.println("空请求。");
	}catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


}
