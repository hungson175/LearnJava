package phs.learn.jedis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;


/**
 * Report: 
 * 
 * SET 7x time (local redis)
 * PIPELINE: put 100,000 items in 150 millis
 * NO_PIP: put 100,000 items in 1161 millis
 * 
 * GET key &values: 30x using pipeline , and 100x if pipeline size of 10000
 * Count: 100035
 * WITH_PIPELINE: #items: 100000 , #scan batch size: 1000, #time:7001
 * Count: 100034
 * NO___PIPELINE: #items: 100000 , #time:201657
 * 
 * 
 * 
 * GET WITH PIPELINE:
 * 
 * WITH_PIPELINE: #items: 1000000 , #scan batch size: 100000, #time:20877 MEM/MAX: 128MB/256MB -Xms64m
 * 
 * WITH_PIPELINE: #items: 1000000 , #scan batch size: 10000, #time:24914, MEM/MAX: 50/250 MB
 * WITH_PIPELINE: #items: 1000000 , #scan batch size: 10000, #time:26414, MEM/MAX: 20MB/64MB  -Xms64m
 * 
 * WITH_PIPELINE: #items: 1000000 , #scan batch size: 1000, #time:69550 , MEM/MAX: 50/250 MB
 * WITH_PIPELINE: #items: 1000000 , #scan batch size: 1000, #time:70950 , MEM/MAX: 30MB/128MB -Xms128m -Xmx256m
 * WITH_PIPELINE: #items: 1000000 , #scan batch size: 1000, #time:70861 , MEM/MAX: 20MB/64MB  -Xms64m
 * 
 *  
 * 
 */
public class LearnPipeline {
	private static final int TOTAL_SIZE = 1000000;
	private static final int SCAN_BATCH_SIZE = 10000;

	public interface IToBeCountedTime {
		void process();
	}

//	private static final int REDIS_PORT = 6379;
	private static final int REDIS_PORT = 6399;
	private static final String REDIS_HOST = "10.30.58.100";
	private static final int SO_TIMEOUT = 20000;
	private static RandomString randomGenerator = new RandomString(32);
	public static void main(String[] args) {
		//testing approach: insert 1000000 items into redis
		// item: string -> byte[]
		// then benmark: 10000 items
		// - get those items from redis normally
		// - using pipeline
		
		long durPipe = new TimeMeter(new IToBeCountedTime() {
			
			@Override
			public void process() {
				getDataWithPipeline();
			}
		}).executeTime();
		System.out.printf("WITH_PIPELINE: #items: %d , #scan batch size: %d, #time:%d \n",TOTAL_SIZE,SCAN_BATCH_SIZE,durPipe);
//		long durNoPipe = new TimeMeter(new IToBeCountedTime() {
//			
//			@Override
//			public void process() {
//				getDataWithoutPipeline();
//			}
//		}).executeTime();
//		System.out.printf("NO___PIPELINE: #items: %d , #time:%d \n",TOTAL_SIZE,durNoPipe);

	}

	private static void getDataWithPipeline() {
		try (
				JedisPool pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT,SO_TIMEOUT);			
				Jedis jedis = pool.getResource();)
		{		
			String cursor = "0";
			int cnt = 0;
			do {
				ScanParams params = new ScanParams().count(SCAN_BATCH_SIZE);
				ScanResult<String> scan = jedis.scan(cursor, params);
				cursor = scan.getStringCursor();
				List<String> uids = scan.getResult();
				Pipeline pipelined = jedis.pipelined();
				for(String uid : uids) {
					pipelined.get(uid.getBytes());
					cnt++;
				}
				List<Object> list = pipelined.syncAndReturnAll();
			} while (cnt < TOTAL_SIZE);
			System.out.println("Count: " + cnt);
		}
	}
	
	private static void getDataWithoutPipeline() {
		long startTime = System.currentTimeMillis();
		try (
				JedisPool pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT,SO_TIMEOUT);			
				Jedis jedis = pool.getResource();)
		{		
			String cursor = "0";
			int cnt = 0;
			int diff = 0;
			do {
				ScanParams params = new ScanParams().count(SCAN_BATCH_SIZE);
				ScanResult<String> scan = jedis.scan(cursor, params);
				cursor = scan.getStringCursor();
				List<String> uids = scan.getResult();
				List<byte[]> list = new LinkedList<byte[]>();
				for(String uid : uids) {
					list.add(jedis.get(uid.getBytes()));
					cnt++;
					diff++;
					if ( diff == 1000) {
						System.out.println("sub-cnt" + cnt + " ## time: " + (System.currentTimeMillis() - startTime));
						diff = 0;
					}
				}
			} while (cnt < TOTAL_SIZE);
			System.out.println("Count: " + cnt);
		}
	}
	

	static class TimeMeter {
		private IToBeCountedTime processor;
		
		public TimeMeter(IToBeCountedTime processor) {
			super();
			this.processor = processor;
		}

		public long executeTime() {
			long startTime = System.currentTimeMillis(); 
			this.processor.process();
			long dur = System.currentTimeMillis() - startTime;
			return dur;
		}
	}
	
	private static void generateItemsPipeline(final int N) {
		try (
				JedisPool pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT,SO_TIMEOUT);			
				Jedis jedis = pool.getResource();)
		{
			
			final HashMap<String, byte[]> data = new HashMap<String, byte[]>();
			for(int i = 0 ; i < N ; i++) {
				String key = generateKey(32);
				byte[] value = key.getBytes();
				data.put(key,value );
			}
			
			IToBeCountedTime processor = new IToBeCountedTime() {
				
				@Override
				public void process() {
					Pipeline pipelined = jedis.pipelined();
					for(String key : data.keySet()) {
						pipelined.set(key.getBytes(), data.get(key));
					}
					pipelined.sync();
				}
			};
			TimeMeter meter = new TimeMeter(processor);
			long duration = meter.executeTime();
			System.out.printf("PIPELINE: put %d items in %d millis\n",N,duration);	
		}
	}
	
	private static void generateItemsNoPipeline(int n) {
		try (
				JedisPool pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT,SO_TIMEOUT);			
				Jedis jedis = pool.getResource();)
		{
			
			final HashMap<String, byte[]> data = new HashMap<String, byte[]>();
			for(int i = 0 ; i < TOTAL_SIZE ; i++) {
				String key = generateKey(32);
				byte[] value = key.getBytes();
				data.put(key,value );
			}
			
			IToBeCountedTime processor = new IToBeCountedTime() {
				
				@Override
				public void process() {

					for(String key : data.keySet()) {
						jedis.set(key.getBytes(), data.get(key));
					}

				}
			};
			TimeMeter meter = new TimeMeter(processor);
			long duration = meter.executeTime();
			System.out.printf("NO_PIP: put %d items in %d millis\n",n,duration);
		}
	}


	private static String generateKey(int length) {
		return randomGenerator.nextString();
	}

}


