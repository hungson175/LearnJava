package vng.wad.sonph.unique_users.utils;

public class NoLogger implements ILogger {

	@Override
	public void logError(String message) {
		//do nothing
	}

	@Override
	public void logInfo(String message) {
		//do nothing
	}

	@Override
	public void logError(Exception e) {
	}

	@Override
	public void logInfo(String message, String location) {
	}

}
