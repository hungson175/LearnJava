package vng.wad.sonph.unique_users.utils;


public interface ILogger {
	//void logError(String message);
	void logInfo(String message);
	void logError(Exception e);
	void logError(String message);
	void logInfo(String message,String location);
}
