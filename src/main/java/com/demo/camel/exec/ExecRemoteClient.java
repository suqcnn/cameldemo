package com.demo.camel.exec;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ExecRemoteClient {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("127.0.0.1", 8999), 2000);

		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			if (wt.ready()) {
				System.out.println("wt.ready...........");
				String str = wt.readLine();
				out.writeUTF(str);
				out.flush();

				if (str.equals("end")) {
					break;
				}

			} else {
				try {
					System.out.println("wt.else...........");
					out.writeUTF("aaaaa");
					out.flush();
					Thread.sleep(100);
				} catch (Exception ex) {
					ex.printStackTrace();
					break;
				}
			}
		}

	}
}
