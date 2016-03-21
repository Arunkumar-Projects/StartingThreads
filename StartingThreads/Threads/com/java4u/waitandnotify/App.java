package com.java4u.waitandnotify;

class Processor {

	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Producer method .......");
			wait();
			System.out.println("Producer method again......");
		}
	}

	public void consume() throws InterruptedException {
		Thread.sleep(2000);
		synchronized (this) {
			System.out.println(" Consumer method....... ");
			Thread.sleep(3000);
			notify();
		}
	}

}

public class App {

	static Processor processor= new Processor();

	public static void main(String[] args) {
		Thread t1= new Thread(new Runnable() {
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2= new Thread(new Runnable() {
			public void run() {
				try {
					processor.consume();
				} catch (InterruptedException e) {
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
