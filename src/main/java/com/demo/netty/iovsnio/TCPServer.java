package com.demo.netty.iovsnio;
import java.io.*;
import java.net.*;
public class TCPServer {
	public static void main(String[] args)throws IOException{
        ServerSocket listen = new ServerSocket(5050);
        
        Socket server  = listen.accept();
        InputStream in = server.getInputStream();
        OutputStream out = server.getOutputStream();
        char c = (char)in.read();
        System.out.println(" ’µΩ:" + c);
        out.write('s');
        
        out.close();
        in.close();
        server.close();
        listen.close();
    }
}
