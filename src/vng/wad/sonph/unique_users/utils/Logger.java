package vng.wad.sonph.unique_users.utils;

public class Logger {

	private static ILogger logger = null;

	public static ILogger getDefaultLogger() {
		if ( logger == null) {
			logger = new ConsoleLogger();
		}
		return logger;
	}

}
