package phs.learn.sql.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import phs.learn.sql.MySQLAccess;
import vng.wad.rd.unique.users.hll.CardinalityMergeException;
import vng.wad.rd.unique.users.hll.HyperLogLog;

public class LearnMySQLTest {
	private static final int ITEM_ID_INDEX = 9;
	private static final int SITE_ID_INDEX = 4;
	private static final int CAMP_ID_INDEX = 8;
	private static final int ZONE_ID_INDEX = 6;
	private static final int USER_ID_INDEX = 11;

	final private static String[] FILE_NAMEs= {
		"/media/hungson175/37BA841367ED8F7A/Logs/server_58728080_2015_02_15_02.log",
		"/media/hungson175/37BA841367ED8F7A/Logs/server_58728080_2015_02_15_03.log",
		"/media/hungson175/37BA841367ED8F7A/Logs/server_58728080_2015_02_15_04.log"
	};
	private static final int BASKET_SIZE = 13;
	@Test
	public void test() {
		MySQLAccess ma = new MySQLAccess();
		ma.clearData();
		HyperLogLog mainHLL = new HyperLogLog(BASKET_SIZE);
		HyperLogLog[] hll = new HyperLogLog[FILE_NAMEs.length];
		HashSet<String> users = new HashSet<String>();		
		try {
			for(int i = 0 ; i < FILE_NAMEs.length ; i++) {
				hll[i] = createHLLData(FILE_NAMEs[i],users);			
				ma.writeBytesData(0, 1, hll[i].getBytes());
			}
			mainHLL = hll[0];
			mainHLL = (HyperLogLog) mainHLL.merge(hll);
			HyperLogLog dbHLL = getMergingHLLsFromDB(ma);
			
			Assert.assertEquals(mainHLL.cardinality(), dbHLL.cardinality());
			System.out.println("#Exact users: " + users.size());
			System.out.println("#Estimated user: normal hll = " + mainHLL.cardinality() + " | db hll = " + dbHLL.cardinality());
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HyperLogLog getMergingHLLsFromDB(MySQLAccess ma) {
		ArrayList<byte[]> all = ma.getAllBytesArray();
		HyperLogLog[] hll = new HyperLogLog[all.size()];
		try {
			for(int i = 0 ; i < all.size(); i++) {
				hll[i] = HyperLogLog.Builder.build(all.get(i));
			}
			HyperLogLog mhll = new HyperLogLog(BASKET_SIZE);
			mhll = (HyperLogLog) mhll.merge(hll);
			return mhll;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (CardinalityMergeException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	private HyperLogLog createHLLData(String fname, HashSet<String> users) {
		HyperLogLog hll = new HyperLogLog(BASKET_SIZE);
		try (BufferedReader br = new BufferedReader(new FileReader(new File(fname)))) {
			for(String line = null; (line = br.readLine()) != null ; ) {
				String[] words = line.split(",");
				String userId = words[USER_ID_INDEX];
				hll.offer(userId);
				users.add(userId);
			}
			return hll;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
