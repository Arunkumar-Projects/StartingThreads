package com.java4u.threads;

class CounterRunnable implements Runnable {

	@Override
	public void run() {

		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}

class CounterThread extends Thread {
	public void run() {
		for (int i = 10; i < 20; i++) {
			System.out.println(i);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

public class StartingThreads {

	public static void main(String[] args) {

		Thread t1= new Thread(new CounterRunnable());
		CounterThread t2= new CounterThread();
		t1.start();
		t2.start();
		
		try{
			t1.join();
			t2.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		System.out.println("Finished ..... Exceution....");
	}
	

}
