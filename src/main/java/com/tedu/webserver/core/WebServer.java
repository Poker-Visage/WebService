package com.tedu.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
	private ServerSocket server;
	private ExecutorService threadPool;
	
	public WebServer() {
	try {
		server=new ServerSocket(8080);
		threadPool=Executors.newFixedThreadPool(40);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	private void start() {
	try {
		while(true) {
			Socket socket=server.accept();
			threadPool.execute(new ClientHandler(socket));
			
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	public static void main(String[] args) {
		WebServer server=new WebServer();
		server.start();
	}
}
