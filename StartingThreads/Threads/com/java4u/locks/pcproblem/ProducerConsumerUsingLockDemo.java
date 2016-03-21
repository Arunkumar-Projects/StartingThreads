package com.java4u.locks.pcproblem;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProduderConsumerImpl {
	private static final int CAPACITY = 10;
	private final Queue queue = new LinkedList<>();
	private final Random random = new Random();

	private Lock alock = new ReentrantLock();
	private final Condition bufferFull = alock.newCondition();
	private final Condition bufferEmpty = alock.newCondition();

	public void put() throws InterruptedException {
		alock.lock();
		try {
			while (true) {
				while (queue.size() == CAPACITY) {
					System.out.println(Thread.currentThread().getName() + " : Buffer is full, waiting");
					bufferEmpty.await();
				}
				int number = random.nextInt(100);
				boolean isAdded = queue.offer(number);
				if (isAdded) {
					System.out.printf("%s added %d into queue %n", Thread.currentThread().getName(), number);

					// signal consumer thread that, buffer has element now
					System.out.println(
							Thread.currentThread().getName() + " : Signalling that buffer is no more empty now");
					bufferFull.signalAll();
				}
			}
		} finally {
			alock.unlock();
		}
	}

	public void get() throws InterruptedException {
		alock.lock();
		try {
			while (true) {
				while (queue.size() == 0) {
					System.out.println(Thread.currentThread().getName() + "  :Buffer is empty and waiting!!");
					bufferFull.await();
				}
				Integer value = (Integer) queue.poll();
				if (value != null) {
					System.out.printf("%s consumed %d from queue %n", Thread.currentThread().getName(), value);
					System.out.println(Thread.currentThread().getName() + " : Signalling that buffer may be empty now");
					bufferEmpty.signalAll();
				}
			}
		} finally {
			alock.unlock();
		}
	}

}

class Producer extends Thread {
	ProduderConsumerImpl pc;

	public Producer(ProduderConsumerImpl sharedObject) {
		super("PRODUCER");
		this.pc = sharedObject;
	}

	@Override
	public void run() {
		try {
			pc.put();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Consumer extends Thread {
	ProduderConsumerImpl pc;

	public Consumer(ProduderConsumerImpl sharedObject) {
		super("CONSUMER");
		this.pc = sharedObject;
	}

	@Override
	public void run() {
		try {
			pc.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

public class ProducerConsumerUsingLockDemo {

	public static void main(String[] args) {
		ProduderConsumerImpl sharedObject = new ProduderConsumerImpl();
		Producer p = new Producer(sharedObject);
		Consumer c = new Consumer(sharedObject);
		p.start();
		c.start();
	}

}
