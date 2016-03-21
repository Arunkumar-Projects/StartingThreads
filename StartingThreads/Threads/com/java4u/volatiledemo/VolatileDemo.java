package com.java4u.volatiledemo;

class Worker implements Runnable {

	// volatile is declared so that the isTerminated is not cached.
	private volatile boolean isTerminated = false;

	@Override
	public void run() {
		while(!isTerminated){
			System.out.println("Hello from Worker Thread ......");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

}

public class VolatileDemo {

	public static void main(String[] args) throws InterruptedException {
		Worker worker= new Worker();
		Thread t1= new Thread(worker);
		t1.start();
		Thread.sleep(3000);
		worker.setTerminated(true);
		System.out.println("Finished.......");
		
	}

}
