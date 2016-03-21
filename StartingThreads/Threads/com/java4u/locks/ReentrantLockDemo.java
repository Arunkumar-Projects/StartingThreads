package com.java4u.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

	private static int counter = 0;
	private static Lock lock = new ReentrantLock();

	public static void increament() {
		lock.lock();
		counter++;
		lock.unlock();
	}

	public static void first() {
		for (int i = 0; i < 1000; i++) {
			increament();
		}
	}

	public static void second() {
		for (int i = 0; i < 1000; i++) {
			increament();
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				first();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				first();
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

		System.out.println("count value at end :: " + counter);
	}

}
