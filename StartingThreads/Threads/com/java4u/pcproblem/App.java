package com.java4u.pcproblem;

import java.util.ArrayList;
import java.util.List;

class Processor {
	private List<Integer> list = new ArrayList<>();
	private static int LIMIT = 10;
	private Object lock = new Object();

	public void produce() throws InterruptedException {
		int value = 0;
		while (true) {
			synchronized (lock) {
				while (this.list.size() == LIMIT) {
					lock.wait();
				}
				this.list.add(value);
				System.out.println("Producer -- Produced the value ::" + value);
				value++;
				lock.notify();
			}
		}
	}

	public void consume() throws InterruptedException {
		while(true){
			
			synchronized (lock) {
				while (this.list.size() == 0) {
					lock.wait();
				}
				int value = this.list.remove(0);
				System.out.println("Consumer -- Consumed the value ::" + value);
				lock.notify();
				
			}
			Thread.sleep(1000);
		}
	}

}

public class App {

	static Processor processor = new Processor();

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.consume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
