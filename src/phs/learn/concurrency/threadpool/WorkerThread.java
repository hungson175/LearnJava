package phs.learn.concurrency.threadpool;

import org.junit.Assert;

import phs.learn.concurrency.threadpool.ThreadsPool.IJob;
import vng.wad.sonph.unique_users.utils.Logger;

public class WorkerThread extends Thread {
	static int count = 0;
	private JobsList jobs;
	private boolean cancelled;
	private IJob currentJob; 
	public WorkerThread(JobsList jobs) {
		super("Worker thread #" + (++count));
		this.jobs = jobs;
		this.cancelled = false;
	}
	
	@Override
	public void run() {
		while ( ! cancelled ) {
			try {
				currentJob = jobs.pop();
				Assert.assertNotNull(currentJob);
				currentJob.execute();
			} catch (InterruptedException e) {
				this.cancelled = true;
			}
			
		}
		Logger.getDefaultLogger().logInfo("The  worker-thread #" + this.getName() + " is stopped");
	}

	public void tryTerminate() {
		currentJob.tryCancel();
		this.cancelled = true;
		this.interrupt(); //signal interrupt-flag, 
	}

}