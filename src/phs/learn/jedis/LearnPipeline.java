package phs.learn.jedis;

import java.awt.MultipleGradientPaint.CycleMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile;

import com.google.protobuf.InvalidProtocolBufferException;


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
	private static final int[] AGE_BOUNDS = {0,18,25,35,45,55,65,999};
	public interface IToBeCountedTime {
		void process();
	}

//	private static final int REDIS_PORT = 6379;
	private static final int REDIS_PORT = 6399;
	private static final String REDIS_HOST = "10.30.58.100";
	private static final int SO_TIMEOUT = 30000;
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

	static HashMap<Integer,Integer> countGender = new HashMap<Integer, Integer>();
	static TreeMap<Integer,Integer> countYear = new TreeMap<Integer, Integer>();
	private static int[] countAgeRangeMDQ;
	private static int cntMDQDefects;
	private static void getDataWithPipeline() {
		try (
				JedisPool pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT,SO_TIMEOUT);			
				Jedis jedis = pool.getResource();)
		{		
			String cursor = "0";
			int cnt = 0;
			countAgeRangeMDQ = new int[AGE_BOUNDS.length-1];
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
				ArrayList<Object> list = new ArrayList<Object>();
				list.addAll(pipelined.syncAndReturnAll());
				Assert.assertEquals(list.size(), uids.size());
				for(int i = 0 ; i < list.size() ; i++) {
					Object temp = list.get(i);
					String uid = uids.get(i);
					if ( temp instanceof byte[]) {
						byte[] bytes = (byte[]) temp;
						try {
							AdtimaProfile profile = AdtimaProfile.parseFrom(bytes);			
							validateGenders(profile,uid);
							validateBirthYear(profile,uid);
							validateAgeRangeByMDG(profile);
						} catch (InvalidProtocolBufferException e) {
							//just skip
						}
					}						
				}
			} while (cnt < TOTAL_SIZE && !cursor.equals("0"));			
			System.out.println("Count: " + cnt);
			int sum = 0;
			for(Integer key : countGender.keySet()) {
				System.out.printf("Gender: Count(%d) = %d, percentage = %.1f \n",key,countGender.get(key),((1.0 * countGender.get(key))/ cnt)*100);
				sum += countGender.get(key);
			}
			System.out.println("Check sum = " + sum);
			System.out.println("------ Ages -----");
			int sumAges = 0;
			int[] countRange = new int[AGE_BOUNDS.length-1];
			for(Integer ages : countYear.keySet()) {
//				System.out.printf("Years: Count(%d) = %d, percentage = %.1f \n",ages,countYear.get(ages),((1.0 * countYear.get(ages))/ cnt)*100);
				sumAges += countYear.get(ages);
				int rangeIndex = findIndex(ages);
				if ( rangeIndex >= 0 ) {					
					countRange[rangeIndex] += countYear.get(ages);
				}
			}
			for(int i = 0 ; i < AGE_BOUNDS.length - 1 ; i++) {
				System.out.printf("Ages: CountRange[%d,%d] = %d, percentage = %.1f\n",AGE_BOUNDS[i],AGE_BOUNDS[i+1]-1,countRange[i],(100.0*countRange[i])/sumAges);
			}
			System.out.println("Check sum ages= " + sumAges);
			System.out.println("------ Ages MDQ-----");
			int sumAgeMDQ = 0;
			for(int i = 0 ; i < AGE_BOUNDS.length - 1 ; i++) sumAgeMDQ += countAgeRangeMDQ[i];
			for(int i = 0 ; i < AGE_BOUNDS.length - 1 ; i++) {
				System.out.printf("Ages: CountRange[%d,%d] = %d, percentage = %.1f\n",AGE_BOUNDS[i],AGE_BOUNDS[i+1]-1,countAgeRangeMDQ[i],(100.0*countAgeRangeMDQ[i])/sumAgeMDQ);
			}
			System.out.println("Check sum ages MDQ= " + sumAgeMDQ);
			System.out.println("Item with more than one age range: " + cntMDQDefects);
		}
	}
	
	private static int findIndex(int ages) {
		int index = -1;
		for(int i = AGE_BOUNDS.length -1 ; i >=0 ; i--) 
			if ( ages >= AGE_BOUNDS[i]) return i;
		return index;		
	}

	private static void validateBirthYear(AdtimaProfile profile, String uid) {
		long dob = 1L * profile.getDob() * 1000;
		int currentYear = new java.util.Date(System.currentTimeMillis()).getYear() + 1900;
		if ( dob !=0  ) {
			java.util.Date date = new java.util.Date(dob);
			@SuppressWarnings("deprecation")
			int y = date.getYear() + 1900;
			int age = currentYear - y;
			Integer c = countYear.get(age);
			int next = 1;
			if ( c != null ) next = c.intValue() + 1;
			countYear.put(age, next);
		}
	}
	
	
//	val AGE_TEEN = createBit(4)
//	val AGE_18_TO_24 = createBit(5)
//	val AGE_25_TO_34 = createBit(6)
//	val AGE_35_TO_44 = createBit(7)
//	val AGE_45_TO_54 = createBit(8)
//	val AGE_55_TO_64 = createBit(9)
//	val AGE_ABOVE_65 = createBit(10)
	private static void validateAgeRangeByMDG(AdtimaProfile profile) {
		int index = -1;
		int cnt = 0;
		long mask = profile.getMqinfo();
		for(int i = 4 ; i <= 10 ; i++) {
			if ( ((mask >> (64-i)) &  1L) != 0) {
				index = i;
				cnt++;
			}
		}
		if ( cnt > 1) {
			cntMDQDefects++;
		} else {
			Assert.assertTrue(cnt <= 1);
			Assert.assertTrue(index == -1 || (index >= 4 && index <= 10));
			if ( index != -1)
				countAgeRangeMDQ[index-4]++;
		}
	}

	//val MALE = createBit(1)
	//val FEMALE = createBit(2)
	private static void validateGenders(AdtimaProfile profile, String uid) {
		int gender = profile.getGender();
		increaseCount(gender);
		if ( gender == 3) {
			System.out.println(uid);
		}
	}

	private static void increaseCount(int gender) {		
		Integer value = countGender.get(gender);
		int next = 1;
		if ( value != null ) next = value.intValue() + 1;	
		countGender.put(gender,next);
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
		try (			// TODO Auto-generated constructor stub
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


