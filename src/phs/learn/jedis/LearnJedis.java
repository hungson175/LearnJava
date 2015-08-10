package phs.learn.jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import vng.wad.rd.unique.users.hll.HyperLogLog;
import vng.wad.sonph.unique_users.utils.Converters;
import vng.wad.sonph.unique_users.utils.Logger;
import ads.common.protobuf.AdtimaProfileProtoBuf;
import ads.common.protobuf.AdtimaProfileProtoBuf.AdtimaProfile;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 
object MediaQuarkProfileBitSet {
def createBit(n: Int) = 1L << (64 - n)

val MALE = createBit(1)
val FEMALE = createBit(2)
val AFFLUENCE = createBit(3)
val AGE_TEEN = createBit(4)
val AGE_18_TO_24 = createBit(5)
val AGE_25_TO_34 = createBit(6)
val AGE_35_TO_44 = createBit(7)
val AGE_45_TO_54 = createBit(8)
val AGE_55_TO_64 = createBit(9)
val AGE_ABOVE_65 = createBit(10)
val DEVICE_DESKTOP = createBit(11)
val DEVICE_TABLET = createBit(12)
val DEVICE_MOBILE = createBit(13)
val OS_WINDOWS = createBit(14)
val OS_MAC = createBit(15)
val OS_LINUX = createBit(16)
val OS_ANDROID = createBit(17)
val OS_BLACKBERRY = createBit(18)
val CAT_ART = createBit(19)
val CAT_AUTOMOTIVE = createBit(20)
val CAT_BUSINESS = createBit(21)
val CAT_CAREER = createBit(22)
val CAT_EDUCATION = createBit(23)
val CAT_FAMILY = createBit(24)
val CAT_HEALTH = createBit(25)
val CAT_FOOD = createBit(26)
val CAT_HOBBIES = createBit(27)
val CAT_HOME = createBit(28)
val CAT_LAW = createBit(29)
val CAT_NEWS = createBit(30)
val CAT_PERSONAL = createBit(31)
val CAT_SOCIETY = createBit(32)
val CAT_SCIENCE = createBit(33)
val CAT_PETS = createBit(34)
val CAT_SPORT = createBit(35)
val CAT_FASHION = createBit(36)
val CAT_TECHNOLOGY = createBit(37)
val CAT_TRAVEL = createBit(38)
val CAT_REAL_ESTATE = createBit(39)
val CAT_SHOPPING = createBit(40)
val CAT_RELIGION = createBit(41)

}

mq & MediaQuarkProfileBitSet.MALE != 0
*/
public class LearnJedis {
	private static final String SONPH_COOKIE = "2000.b900fd308d45641b3d54.1432535984220.c30edd36";
	private static final String SONPH_COOKIE_FIREFOX = "2000.3e7b29cafeb817e64ea9.1435834903302.1ba9cbe0";
	private static final String CURRENT_COOKIE = "2000.138d9ee35fb0b6eeefa1.1419490874739.f7e4a8af";	
	private static final String COOKIE_GENDER_3 = "2001.0508f37e0a3ae364ba2b.1434460212208.029cefd2";
	private static final int BATCH_SIZE = 1000;
	private static final int BASKET_LOG2M = 13;
	private static final int TIMEOUT = 20000;
	private static final int REDIS_PORT = 6399;
	private static final String REDIS_HOST = "10.30.58.100";	
//	private static final int REDIS_PORT = 6379;
//	private static final String REDIS_HOST = "127.0.0.1";

	
	public static void main(String[] args) {
		AdtimaProfile profile = getProfile(CURRENT_COOKIE);		
		long dob = profile.getDob();
		long mqinfo = profile.getMqinfo();
		System.err.println("DOB:"+dob);
		System.out.println("MQ info: " + mqinfo);
		System.out.println("DOB: " + Converters.toHumanReadableTime(dob*1000));
		System.out.println("Gender by getGender: " + profile.getGender()); //1: male //0: female
		validateGender(profile);
		validateAge(profile); 
		//(System.currentTimeMillis / 1000 - adtimaProfile.dob) =  seconds lived  
//		int gender = profile.getGender();
//		System.out.println("Gender: " +gender);
//		System.out.println("DOB: " + Converters.toHumanReadableTime(dob*1000));
//		getAllKeys();
//		benmarkGetProfiles();
		

	}
	private static final int[] AGE_BOUNDS = {0,18,25,35,45,55,65,999};
	private static void validateAge(AdtimaProfile profile) {
		int age = getZaloAge(profile);
		int ageLowerBound = getMdqAgeLowerBound(profile,AGE_BOUNDS);
		System.out.println("Zalo age: " + age + " ## MDQ lower bounds: " + ageLowerBound);
		
	}

//			val AGE_TEEN = createBit(4)
//			val AGE_18_TO_24 = createBit(5)
//			val AGE_25_TO_34 = createBit(6)
//			val AGE_35_TO_44 = createBit(7)
//			val AGE_45_TO_54 = createBit(8)
//			val AGE_55_TO_64 = createBit(9)
//			val AGE_ABOVE_65 = createBit(10)
	private static int getMdqAgeLowerBound(AdtimaProfile profile,
			int[] ageBounds) {
		int index = -1;
		int cnt = 0;
		long mask = profile.getMqinfo();
		for(int i = 4 ; i <= 10 ; i++) {
			if ( ((mask >> i) &  1L) != 0) {
				index = i;
				cnt++;
			}
		}
		Assert.assertTrue(cnt <= 1);
		Assert.assertTrue(index == -1 || (index >= 4 && index <= 10));
		return index == -1 ? -1 : ageBounds[index-4];
	}

	private static int getZaloAge(AdtimaProfile profile) {
		return (int) ((System.currentTimeMillis() - 1L * profile.getDob() * 1000) / (365L*24*60*60*1000));
	}

	//val MALE = createBit(1)
	//val FEMALE = createBit(2)
	private static void validateGender(AdtimaProfile profile) {
		int gender = profile.getGender();
		boolean isZaloMale = (gender == 1); 
		boolean isZaloFemale = (gender == 0);
		long mask = profile.getMqinfo();
		boolean isMale = ((mask >> (64-1)) & 1L) != 0;
		boolean isFemale = ((mask >> (64-2)) & 1L ) != 0;
		
		System.out.println("Zalo gender: " + gender + " ### MDQ: (isFemale,isMale) = " + isFemale + ","+ isMale );
//		Assert.assertEquals(isZaloFemale, isFemale);
//		Assert.assertEquals(isZaloMale, isFemale);
		
	}

	private static void benmarkGetProfiles() {
		// TODO Auto-generated method stub
		HashMap<String, AdtimaProfile> profiles = new HashMap<String, AdtimaProfileProtoBuf.AdtimaProfile>();
		try (
				JedisPool pool = new JedisPool(new JedisPoolConfig(),REDIS_HOST,REDIS_PORT);			
				Jedis jedis = pool.getResource();)
		{
			long startTime = System.currentTimeMillis();
			String cursor = "0";
			ScanParams params = new ScanParams().count(BATCH_SIZE);
			ScanResult<String> scan = jedis.scan(cursor, params);
			cursor = scan.getStringCursor();
			List<String> uids = scan.getResult();
			Pipeline pipeline = jedis.pipelined();
			for(String uid : uids) {
			}			
			pipeline.sync();

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
	private static void getAllKeys() {
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
				ScanParams params = new ScanParams().count(BATCH_SIZE);
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


}
