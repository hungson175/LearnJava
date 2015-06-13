package vng.wad.sonph.unique_users.utils;

import java.sql.Date;
import java.util.concurrent.TimeUnit;


public class Helpers {

	private static final int DEFAULT_DEEP = 3;
	public static String extractPosition(Exception e) {
		return extractPosition(e, DEFAULT_DEEP);
	}
	public static String extractPosition(Exception e,int deep) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		for(int i = 0 ; i < stackTrace.length ; i++) {
			StackTraceElement element = stackTrace[i];
			String s = element.toString();
			if ( s.startsWith("vng.wad.")) {
				StringBuilder b = new StringBuilder();
				for(int j = 0 ; j < deep && i+j < stackTrace.length; j++) {
					b.append(stackTrace[i+j].toString()+"\n");
				}
				return b.toString();
			}
		}
		return null;
	}
	
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static long usedMemInMBs() {
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		return bytesToMegabytes(memory);
	}
	
	private static final long MEGABYTE = 1024L * 1024L;
	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}

}
