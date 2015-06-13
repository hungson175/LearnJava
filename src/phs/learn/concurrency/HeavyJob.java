package phs.learn.concurrency;

import phs.learn.concurrency.threadpool.ThreadsPool.IJob;
import vng.wad.sonph.unique_users.utils.Logger;

public class HeavyJob implements IJob {

	private int millis;
	private int jid;
	static int jobId = 0 ;
	public HeavyJob(int millis) {
		this.millis = millis;
		this.jid = ++jobId;
	}

	@Override
	public void execute() {
		try {
			Thread.sleep(this.millis);
		} catch (InterruptedException e) {
			Logger.getDefaultLogger().logError(e);
		}
		System.out.println("TIME : " + this.millis + " -- Thread #" + Thread.currentThread().getId() + ": done job " + jid);
	}

	@Override
	public void tryCancel() {
		// TODO later
	}

}