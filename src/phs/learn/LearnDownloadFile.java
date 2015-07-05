package phs.learn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class LearnDownloadFile {
	private static final String FILE_PATH = 
			"/media/hungson175/37BA841367ED8F7A/Logs/Zips/2015_05_08/server_58728080_2015_05_08_01.log.zip";
	private static final String FILE_URL = 
			"http://10.30.58.198/compresslog/2015/05/08/server_58728080_2015_05_08_01.log.zip";

	public static void main(String[] args) {
		FileOutputStream fos = null;
		long startTime = System.currentTimeMillis();
		try {
			URL website = new URL(FILE_URL);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			fos = new FileOutputStream(FILE_PATH);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			System.out.println("Duration: " + (System.currentTimeMillis() - startTime));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { fos.close(); } catch (Exception e) {}
		}
	}
}
