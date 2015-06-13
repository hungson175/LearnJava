package phs.learn.sql.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Helpers {
	public static byte[] toByteArray(InputStream is) {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[0xFFFF];
			for(int len; (len = is.read(buffer)) != -1; ) {
				os.write(buffer,0,len);
			}
			os.flush();
			return os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
