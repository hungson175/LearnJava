package vng.wad.sonph.unique_users.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Converters {
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

	public static byte[] blob2BytesArray(Blob blob) throws SQLException {
		return toByteArray(blob.getBinaryStream());
	}

	/**
	 * 
	 * @param connection
	 * @param data
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Blob bytesArray2Blob(Connection connection, byte[] data) throws SQLException, IOException {
		Blob blob = connection.createBlob();
		OutputStream os = blob.setBinaryStream(1);
		os.write(data);
		os.flush();
		os.close();
		return blob;
	}

	/**
	 * Return the java.sql.Date of the given string 
	 * @param dateString with the pattern yyyy-MM-dd
	 * @return return the date if the date is correct, null otherwise
	 */
	public static Date toSQLDate(String dateString) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
			java.util.Date javaDate = df.parse(dateString);
			String outFormat = df.format(javaDate);
			if ( dateString.equals(outFormat)) {
				Date sqlDate = new java.sql.Date(javaDate.getTime());
				return sqlDate;
			} else return null;
		} catch (Exception e) {
			Logger.getDefaultLogger().logError(e);
			return null;
		}
	}

}
