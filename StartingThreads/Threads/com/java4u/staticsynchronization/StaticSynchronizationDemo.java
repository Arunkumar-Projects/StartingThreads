package com.java4u.staticsynchronization;

class Table {
	static synchronized void printTable(int n) {
		for (int i = 1; i < 10; i++) {
			System.out.println(n * i);
		}
	}
}

class MyThread1 implements Runnable{

	@Override
	public void run() {
		Table.printTable(1);
	}
	
}

class MyThread2 implements Runnable{

	@Override
	public void run() {
		Table.printTable(10);
	}
	
}
class MyThread3 implements Runnable{

	@Override
	public void run() {
		Table.printTable(100);
	}
	
}
class MyThread4 implements Runnable{

	@Override
	public void run() {
		Table.printTable(1000);
	}
	
}

public class StaticSynchronizationDemo {
	
	public static void main(String[] args) {
		Thread t1= new Thread(new MyThread1());
		Thread t2= new Thread(new MyThread2());
		Thread t3= new Thread(new MyThread3());
		Thread t4= new Thread(new MyThread4());
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
	}

}
