package com.demo.netty.iovsnio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNIOEchoServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		serv(6000);
	}
	
	private static void serv(int port) throws IOException{
//		#1 Bind server to port
//		#2 Register the channel with the selector to be interested in new Client connections that get accepted
//		#3 Block until something is selected
//		#4 Get all SelectedKey instances
//		#5 Remove the SelectedKey from the iterator
//		#6 Accept the client connection
//		#7 Register connection to selector and set ByteBuffer
//		#8 Check for SelectedKey for read
//		#9 Read data to ByteBuffer
//		#10 Check for SelectedKey for write
//		#11 Write data from ByteBuffer to channel
		System.out.println("Listening for connetcions on port:"+port);
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		ServerSocket ss = serverSocketChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ss.bind(address);
		serverSocketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(true){
			try{
				selector.select();
			}catch(IOException ex){
				ex.printStackTrace();
				break;
			}
			Set readyKeys = selector.selectedKeys();
			Iterator it = readyKeys.iterator();
			while(it.hasNext()){
				SelectionKey key = (SelectionKey)it.next();
				it.remove();
				try{
					if(key.isAcceptable()){
						ServerSocketChannel server = (ServerSocketChannel)key.channel();
						SocketChannel client = server.accept();
						System.out.println("accepted connection from : "+client);
						client.configureBlocking(false);
						client.register(selector, SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
					}
					if(key.isReadable()){
						SocketChannel client = (SocketChannel)key.channel();
						ByteBuffer output = (ByteBuffer)key.attachment();
						client.read(output);
					}if(key.isWritable()){
						SocketChannel client = (SocketChannel)key.channel();
						ByteBuffer output = (ByteBuffer)key.attachment();
//						ByteBuffer bb = new ByteBuffer("");
						output.flip();
						client.write(output);
//						client.write("");
						output.compact();
					}
				}catch(Exception ex){
					ex.printStackTrace();
					key.cancel();
					key.channel().close();
				}
			}
		}
	}

}
