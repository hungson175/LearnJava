package phs.learn.concurrency.threadpool;

import java.util.LinkedList;

import phs.learn.concurrency.threadpool.ThreadsPool.IJob;

/**
 * 
 * @author hungson175
 * Note, this queue will be changed in the future to persistent queue (per over process)
 */
public class JobsList {
	LinkedList<IJob> list = new LinkedList<ThreadsPool.IJob>();
	
	public JobsList() {
		//To be extended: load from persistent storage
	}
	
	public synchronized void add(IJob e) {
		list.add(e);
		this.notifyAll();
		//To be extended: save to persistent storage
	}
	
	/**
	 * 
	 * @return take & remove the first element from the list, block in case of empty.		  
	 * @throws InterruptedException
	 */
	public synchronized IJob pop() throws InterruptedException {
		while (list.isEmpty()) {
			this.wait();
		}
		return list.pop();
		//To be extended: remove from persistent storage`	
	}

	public synchronized void clear() {
		list.clear();
	}

}