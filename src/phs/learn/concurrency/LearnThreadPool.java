package phs.learn.concurrency;

import phs.learn.concurrency.threadpool.ThreadsPool;

public class LearnThreadPool {
	public static void main(String[] args) {
		LearnThreadPool learn = new LearnThreadPool();
		learn.run();
	}

	
	private void run() {
//		testOne();
		testTwo();
//		testThree();
	}

	private void testThree() {
		//Create threadpool 3
		//1.5 x 1s
		//2. 2 x run 2s
		//3, 1 x run 3s
		// => 1,  1,2, 1,3, 1,2, 1
		ThreadsPool pool = new ThreadsPool(3);
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(2000));
		pool.add(new HeavyJob(3000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(2000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(1000));
		System.out.println("\n=====================\n");
	}
	
	private void testTwo() {
		//Create threadpool 1
		//1.5 x 1s
		//2. 2 x run 2s
		//3, 1 x run 3s
		ThreadsPool pool = new ThreadsPool(2);
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(2000));
		pool.add(new HeavyJob(3000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(2000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(1000));
		//        0 | 1  	| 2 	| 3 	| 4 	| 5 	| 6 	|
		// => T1: 1 | e1,3	| -		|-		|e3,1	|e1,1
		// => T2: 2 | -  	| e2,1	|e1,2	|-		|e2,1	|		|
		//=> 1,2,1,3,1, 1,2, 1
		System.out.println("\n=====================\n");
	}
	private void testOne() {
		//Create threadpool 1
		//1.5 x 1s
		//2. 2 x run 2s
		//3, 1 x run 3s
		// => 1,2,3,1,2,1,1,1
		ThreadsPool pool = new ThreadsPool(1);
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(2000));
		pool.add(new HeavyJob(3000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(2000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(1000));
		pool.add(new HeavyJob(1000));
		System.out.println("\n=====================\n");
	}
}
