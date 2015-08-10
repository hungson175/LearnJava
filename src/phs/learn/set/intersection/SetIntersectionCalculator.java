package phs.learn.set.intersection;

import java.util.Arrays;

import org.junit.Assert;

public class SetIntersectionCalculator {
	private static final int MAX_LENGTH = 20;
	private int numberElements;
	private ICountingSet[] a = null;
	private int[] cached;
	private int[] mergeSet;
	
	public SetIntersectionCalculator(ICountingSet[] a) {
		super();
		this.a = a;
		this.numberElements = a.length;
		
		this.cached = new int[1 << numberElements];
		Arrays.fill(cached, -1);
		this.cached[0] = 0;
		
		this.mergeSet = new int[1 << numberElements];
		Arrays.fill(mergeSet, -1);
		this.mergeSet[0] = 0; 
		
		
		Assert.assertTrue( numberElements <= MAX_LENGTH);
	}
	
	/**
	 * Calculate the number of intersection between all sets
	 * @precondition a.lentgth <= @MAX_LENGTH
	 * @param a 
	 * @return
	 */
	public int countIntersections() {
		if ( a.length > 20 ) return -1;
		return countIntersect((1<<numberElements)-1);
	}

	private int countIntersect(int mask) {
		int cntBits = Integer.bitCount(mask);
		if ( cached[mask] != -1) return cached[mask]; //includes case mask = 0 already
		
		if ( cntBits == 1) {
			for(int i = 0 ; i < numberElements ; i++)
				if ( hasBit(mask, i) ) {
					cached[mask] = a[i].cardinality();
					return a[i].cardinality();
				}
			Assert.assertTrue(false);
			return -1;
		}
		
		//complicated case here
		int mul = powMinus1(cntBits-1);
		int merge = mergeSet(mask);		
		int sumKN = calculateSumKN(mask,cntBits);
		int res = merge + sumKN;
		cached[mask] = mul * res;
		return cached[mask];
	}

	private int calculateSumKN(int mask,int n) {
		int sum = 0;
		int[] subMasks = subMask(mask); // all submask with >= 1 elements
		for(int sub : subMasks) {
			sum += powMinus1(Integer.bitCount(sub)) * countIntersect(sub);
		}
		return sum;
	}


	// all submask with >= 1 elements (submask means < mask)
	private int[] subMask(int mask) {
		int n = Integer.bitCount(mask);
		int[] bitPosition = new int[n];
		int k = 0;
		for(int i = 0 ; i < 32 ; i++) 
			if ( hasBit(mask, i)) bitPosition[k++] = i;
		return generateAllMask(bitPosition); //all submask > 0 and submask < mask 
	}

	//all mask > 0 with bitPosition 
	private int[] generateAllMask(int[] bitPosition) {
		int n = bitPosition.length;
		int[] subMask = new int[(1<<n)-2];
		int k = 0;
		for(int positionMask = 1; positionMask < (1<<n)-1 ; positionMask++) {
			subMask[k++] = getBitMask(positionMask,bitPosition);
		}
		Assert.assertEquals((1<<n)-2,k);
		return subMask;
	}

	private int getBitMask(int positionMask, int[] bitPosition) {
		int mask = 0;
		for(int i = 0 ; i < bitPosition.length ; i++ )
			if ( hasBit(positionMask, i)) mask = mask | ( 1 << bitPosition[i]);
		return mask;
	}

	private boolean hasBit(int mask, int i) {
		return ((mask>>i) & 1) != 0;
	}

	private int mergeSet(int mask) {
		if (mergeSet[mask] != -1) return mergeSet[mask];
		ICountingSet s = null;
		for(int i = 0 ; i < numberElements ; i++) {
			if (hasBit(mask,i)) {
				if ( s == null ) 
					s = a[i];
				else s = s.merge(a[i]);
			}
		}
		mergeSet[mask] = s.cardinality();
		return mergeSet[mask];
	}

	private int powMinus1(int n) {
		if ( n % 2 == 0 ) return 1;
		return -1;
	}
}
