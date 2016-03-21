package com.java4u.syncblocks;

public class SyncBlockDemo {

	private static int counter1 = 0;
	private static int counter2 = 0;
	private static Object lock1= new Object();
	private static Object lock2= new Object();

	public static synchronized void add() {
		synchronized (lock1) {
			counter1++;
		}
	}

	public static void addAgain() {
		synchronized (lock2) {
			counter2++;
		}
	}

	public static synchronized void compute() {
		for (int i = 0; i < 1000; i++) {
			add();
			addAgain();
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				compute();
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				compute();
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

		System.out.println("counter 1::  " + counter1 + "     counter 2:: " + counter2);
	}

}
