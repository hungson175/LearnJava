package phs.learn;

import java.util.Arrays;

public class Fibonacci {
	static int N = 50;
	private static int cnt;
	private static long[] f = new long[N+1];
	public static void main(String[] args) {
		cnt = 0 ;
		Arrays.fill(f, -1);
		System.out.println(""+fibo(N)+"###"+cnt);
	}
	private static long fibo(int n) {
		cnt++;
		if ( n <= 1) return 1;
		if ( f[n] != -1 ) return f[n];
		f[n] = fibo(n-1) + fibo(n-2);
		return f[n];
	}
}
