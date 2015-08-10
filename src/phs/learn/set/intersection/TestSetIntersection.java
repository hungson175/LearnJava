package phs.learn.set.intersection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vng.wad.sonph.unique_users.hll.CardinalityMergeException;
import vng.wad.sonph.unique_users.hll.HyperLogLog;


public class TestSetIntersection {

	private static final int BASKET_LOG2M = 13;
	/**
	 * TODO:
	 * - Test the merge function of HLL: c = a.merge(b), assert(a not changed !)
	 */


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMergeHLL() {
		HyperLogLog A = createHyperLogLogWithElements(new byte[] {1,2,3,4,5});
		HyperLogLog B = createHyperLogLogWithElements(new byte[] {4,5,6,7,8,9});

		try {
			HyperLogLog C = (HyperLogLog) A.merge(new HyperLogLog[] {B});
			Assert.assertEquals(9, C.cardinality());
			Assert.assertEquals(5, A.cardinality());
			Assert.assertEquals(6, B.cardinality());
		} catch (CardinalityMergeException e) {
			Assert.fail(e.toString());
		}

	}
	public static HyperLogLog createHyperLogLogWithElements(byte[] elem) {
		HyperLogLog h = new HyperLogLog(BASKET_LOG2M);
		for(byte b : elem) {
			h.offer(b);
		}
		return h;
	}

	@Test
	public void testSetIntersection() {
		Integer[][][] SETs = {
				{
					{1,2},
					{2,3}
				},
				{
					{1,2,3,4,100},
					{3,4,0,0},
					{5,10,3,4,1,2},
					{8,3,5,10,0,4}
				},
				{{1}},
				{{1,-1,10,-10,-2,-3}},
				{
					{1,2,3,4},
					{5,6,7,8},
					{100,200,300},
					{-1},
				},
				{{}},
				{{},{1,2,3,4}},
				{
					{1,2,3,4,100},
					{3,4,0,0},
					{5,10,3,4,1,2},
					{8,3,5,10,0,4},
					{}
				},
				{{1,2,3},{1,2,3},{1,2,3} },
		};
		int[] EXP_SIZE = {1,2,1,6,0,0,0,0,3};
		verify(SETs[0],EXP_SIZE[0]);
		verify(SETs[1],EXP_SIZE[1]);
		verify(SETs[2],EXP_SIZE[2]);
		verify(SETs[3],EXP_SIZE[3]);
		verify(SETs[4],EXP_SIZE[4]);
		verify(SETs[5],EXP_SIZE[5]);
		verify(SETs[6],EXP_SIZE[6]);
		verify(SETs[7],EXP_SIZE[7]);
		verify(SETs[8],EXP_SIZE[8]);
	}
	
	@Test
	public void testSetBigIntersection() {
		Integer[][][] SETs = {
//				{
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//				},
				{
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}
				},
//				{
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20},
//					{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}
//				},
		};
		int[] EXP_SIZE = {20,20,20};
		verify(SETs[0],EXP_SIZE[0]);
//		verify(SETs[1],EXP_SIZE[1]);
//		verify(SETs[2],EXP_SIZE[2]);
	}

	private void verify(Integer[][] SETs, int expectedSize) {
		MySet<Integer>[] s = new MySet[SETs.length];
		for(int i = 0 ; i < s.length ; i++) s[i] = new MySet<Integer>(SETs[i]);
		Assert.assertEquals(expectedSize,new SetIntersectionCalculator(s).countIntersections());
	}
}