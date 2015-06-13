package vng.wad.sonph.unique_users.utils;


public class ConsoleLogger implements ILogger {

	@Override
	public void logInfo(String message) {
		System.out.println(message);
	}

	@Override
	public void logError(Exception e) {
		//e.printStackTrace();
		logError(e.getClass().getName() + ": " + e.getMessage() + " AT " + Helpers.extractPosition(e,3));
	}

	@Override
	public void logError(String message) {
		
		System.err.println(message);
	}

	@Override
	public void logInfo(String message, String location) {
		System.out.println(message + " ### AT LOCATION: " + location);
	}

}
