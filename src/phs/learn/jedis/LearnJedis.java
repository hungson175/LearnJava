package phs.learn.jedis;

import java.util.HashSet;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import vng.wad.rd.unique.users.hll.HyperLogLog;
import ads.common.protobuf.AdtimaProfileProtoBuf;
import ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile;

import com.google.protobuf.InvalidProtocolBufferException;

public class LearnJedis {
	private static final int BASKET_LOG2M = 13;
	private static final int TIMEOUT = 20000;
	private static final int REDIS_PORT = 6399;
	private static final String REDIS_HOST = "10.30.58.100";

	public static void main(String[] args) {
		AdtimaProfile profile = getProfile("2000.ca7f984ee5110c4f5500.1421671028443.870b4329");
//		HashMap<String, AdtimaProfile> profiles = new HashMap<String, AdtimaProfileProtoBuf.AdtimaProfile>();
		HashSet<String> keySet = new  HashSet<String>();
		HyperLogLog hll = new HyperLogLog(BASKET_LOG2M);

		try (
				JedisPool  pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT,TIMEOUT);		
				Jedis jedis = pool.getResource();) {

			long startTime = System.currentTimeMillis();
			String cursor = "0";
			int cnt = 0;
			int cntE = 0;
			do {
				ScanParams params = new ScanParams().count(1000000);
				ScanResult<String> scan = jedis.scan(cursor, params);
				cursor = scan.getStringCursor();
				List<String> uids = scan.getResult();
				for(String uid : uids) {
					try {
//						byte[] bytes = jedis.get(uid.getBytes());
//						AdtimaProfileProtoBuf.AdtimaProfile.parseFrom(bytes);
						cnt++;
						keySet.add(uid);
						hll.offer(uid);
//						System.out.println(uid);
					} catch (Exception e) {
						cntE++;
					}
				}	
				if ( cnt % 1000000 < 1000 ) {
					System.out.println("Rows: " + cnt + " === time: " + (System.currentTimeMillis() - startTime) + " === cursor: " + cursor);
				}
			} while (!cursor.equals("0"));	
			System.out.println("Rows: " + cnt + " ErrorCnt: " + cntE + " === time: " + (System.currentTimeMillis() - startTime));
			int exactCount = keySet.size();
			int estCount = (int) hll.cardinality();
			double f = (estCount * 1.0)/exactCount;
			if ( f > 1.0) f = 1.0/f; 
			System.out.println("Exact: " + exactCount + " ### Estimated: " + estCount + " ### Procent: " + f );
		}
	}

	private static AdtimaProfile getProfile(String uid) {
		try (JedisPool pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT);) {
			try (Jedis jedis = pool.getResource()){				
				byte[] bytes = jedis.get(uid.getBytes());
				AdtimaProfile profile = AdtimaProfileProtoBuf.AdtimaProfile.parseFrom(bytes);
				return profile;
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
				return null;
			} 
		}
	}
}