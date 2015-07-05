package sonph.topcoder;

public class Math {
	public static int GCD(int a,int b) {
		if ( b == 0 ) return a;
		return GCD(b,a %b); //ok even when b > a
	}
	
	/**
	 * Careful with a * b (may be too large for int)
	 * @param a
	 * @param b
	 * @return lowest common multiple
	 */
	public static int LCM(int a,int b) {
		return a * b / GCD(a,b);
	}
}
