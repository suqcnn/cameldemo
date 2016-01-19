package com.demo.netty.iovsnio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("127.0.0.1",6000);
		InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream();
        
        out.write('c');
        char c = (char)in.read();
        System.out.println(" ’µΩ:" + c);
        out.close();
        in.close();
        client.close();
	}

}
