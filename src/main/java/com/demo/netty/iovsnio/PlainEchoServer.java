package com.demo.netty.iovsnio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PlainEchoServer {

	public static void main(String[] args) throws Exception {
		server(2005);
//		ServerSocket serverSocket = new ServerSocket(2005);
//		System.out.println("server start.");
//		while(true){
//			final Socket socket= serverSocket.accept();
//			System.out.println("recieve a request.");
//			new Thread(new Runnable(){
//				@Override
//				public void run() {
//					
//					try {
//						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//						PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
////						while(true){
////							writer.print(reader.readLine());
////							writer.print("a");
////							writer.flush();
//						System.out.println("xx");
//							
////						}
//							reader.close();
//							writer.close();
//						
//					} catch (IOException e) {
//						
//						e.printStackTrace();
//					}finally{
//						try {
//							socket.close();
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//					}
//				}
//			}).start();
//		}
	}
	
	private static void server(int port) throws Exception{
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("server start.");
		while(true){
			final Socket socket= serverSocket.accept();
			System.out.println("recieve a request.");
			new Thread(new Runnable(){
				@Override
				public void run() {
					
					try {
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
						while(true){
							System.out.println("echo to client ");
							writer.print(reader.readLine());
//							writer.print("a");
							writer.flush();
							
						}
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}finally{
						try {
							socket.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

}
