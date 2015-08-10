package phs.learn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

public class DP_Longest_Inc_Sub implements Runnable {

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(null, new DP_Longest_Inc_Sub(), "", 256 * (1L << 20)).start();
	}

	public void run() {
		try {
			long t1 = System.currentTimeMillis();
			if (System.getProperty("ONLINE_JUDGE") != null) {
				in = new BufferedReader(new InputStreamReader(System.in));
				out = new PrintWriter(System.out);
			} else {
				in = new BufferedReader(new FileReader("input.txt"));
				out = new PrintWriter("output.txt");
			}
			Locale.setDefault(Locale.US);
			solve();
			in.close();
			out.close();
			long t2 = System.currentTimeMillis();
			System.err.println("Time = " + (t2 - t1));
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	String readString() throws IOException {
		while (!tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}
		return tok.nextToken();
	}

	int readInt() throws IOException {
		return Integer.parseInt(readString());
	}

	long readLong() throws IOException {
		return Long.parseLong(readString());
	}

	double readDouble() throws IOException {
		return Double.parseDouble(readString());
	}

	// solution

	void solve() throws IOException {
		String s = null;
		while ( (s = readString()) != null) {
			out.println(""+longestIncSub(s));
		}
	}

	private int longestIncSub(String s) {
		int n = s.length();
		int dp[] = new int[n];
		char[] a = s.toCharArray();
		int res = 1;
		dp[0] = 1;
		for(int i = 1;  i < n ; i++) {
			dp[i] = 1;
			for(int k = 0 ; k < i ; k++)
				if ( a[k] <= a[i]) dp[i] = Math.max(dp[i],dp[k]+1);
			res = Math.max(res,dp[i]);
		}
		return res;
	}

}