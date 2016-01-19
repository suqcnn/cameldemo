package com.demo.camel.javaconcurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Callable callable = new Callable(){

			public Object call() throws Exception {
				int a = 3;
				int b = 2;
				System.out.println("cal 3+2");
				return String.valueOf(3+2);
			}
			
		};
		ExecutorService executor = Executors.newCachedThreadPool();
		Future future = executor.submit(callable);
//		while(true){
			if(future.isDone()){
				System.out.println("ok, has done"+future.get());
//				break;
			}else{
				System.out.println("waiting for future back");
			}
//		}
	}

}
