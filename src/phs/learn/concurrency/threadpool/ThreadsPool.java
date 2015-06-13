package phs.learn.concurrency.threadpool;


public class ThreadsPool {
	public interface IJob {
		public void execute();
		public void tryCancel();
	}

	private JobsList jobs;
	private WorkerThread[] workers;
	
	public ThreadsPool(int nthreads) {
		this.jobs = new JobsList();
		this.workers = new WorkerThread[nthreads];
		for(int i = 0 ; i < workers.length ; i++) {
			workers[i] = new WorkerThread(jobs);
			workers[i].start();
		}
	}
	
	public void add(IJob job) {
		jobs.add(job);
	}
	
	/**
	 * Try to stop all the jobs:
	 * 1. Sure: clear all waiting job
	 * 2. Not so sure: stop all running job (it will call IJob.tryCancel() on running job, but the job decides what to do with that call)
	 * 3. Sure: after all running jobs done -> all thread stop
	 */
	public void tryTerminate() {
		jobs.clear();
		for(int i = 0 ; i < workers.length ; i++) {
			workers[i].tryTerminate();
		}
	}
	
}
