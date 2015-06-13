package phs.learn.concurrency;

public class DeadLock {
	static class Friend  {
		private final String name;
		public Friend(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		
		public synchronized void bow(Friend bower) {
			System.out.format("%s : %s has bowed to me %n",  this.name, bower.getName());
			bower.bowBack(this);
		}
		
		private synchronized void bowBack(Friend bower) {
			System.out.format("%s : %s has bowed back to me %n",  this.name, bower.getName());
		} 
	}
	
	public static void main(String[] args) {
		final Friend alp = new Friend("Alp");
		final Friend gap = new Friend("Gap");
		new Thread(new Runnable() {
			@Override
			public void run() {
				alp.bow(gap);
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				gap.bow(alp);
			}
		}).start();
		
	}
}
