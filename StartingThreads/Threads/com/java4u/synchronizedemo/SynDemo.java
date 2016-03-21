package com.java4u.synchronizedemo;

public class SynDemo {
	private static int count = 0;

	public static synchronized void increament(){
		count++;
	}
	public static void process(){
		Thread t1= new Thread(new  Runnable() {
			public void run() {
				for(int i=0;i<1000;i++){
					increament();
				}
			}
		});
		
		Thread t2= new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<1000;i++){
					increament();
				}
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		process();
		System.out.println(count);
	}

}
