package phs.learn.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import phs.learn.sql.utils.Helpers;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Learn_InsertNUpdate_Blob {

//	MYSQL_DB_DRIVER_CLASS=com.mysql.jdbc.Driver
//	MYSQL_DB_URL=jdbc:mysql://localhost:3306/UserDB
//	MYSQL_DB_USERNAME=pankaj
//	MYSQL_DB_PASSWORD=pankaj123
	
	public static class Runner {
		private Connection connection;
		public Runner() {
			try {
//				connection = DriverManager.getConnection(
//						"jdbc:mysql://localhost:3306/test_uuest?"+ 
//						"user=root&password=dangthaison");
				MysqlDataSource ds = getMySQLDataSource();
				connection = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		private MysqlDataSource getMySQLDataSource() {
			MysqlDataSource ds = new MysqlDataSource();
			ds.setURL("jdbc:mysql://localhost:3306/test_uuest");
			ds.setUser("root");
			ds.setPassword("dangthaison");
			return ds;
		}

		private int insertOrUpdate(Date datum, int part, byte[] data) throws IOException {
			String sql = 
					"insert into part_all (datum,part,hll_bytes) values (?,?,?) "; 
			try ( PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setDate(1, datum); //TODO: test this 
				ps.setInt(2, part);
				Blob blobData = blobFromBytesArray(connection,data);
				ps.setBlob(3, blobData);
				ps.executeUpdate();
			} catch (MySQLIntegrityConstraintViolationException e) {
				System.out.println("MySQLIntegrityConstraintViolationException occurs for " + datum + " ## " + part);
				update(datum, part, data);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return -1;
		}

		private void update(Date date, int part, byte[] data) throws IOException {
			String updateSQL = "update part_all set hll_bytes=? where datum = ? AND part = ?";
			try (PreparedStatement ps = connection.prepareStatement(updateSQL)) {
				ps.setBlob(1, blobFromBytesArray(connection, data));
				ps.setDate(2, date);
				ps.setInt(3, part);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		private Blob blobFromBytesArray(Connection connection, byte[] data) throws SQLException, IOException {
			Blob blob = connection.createBlob();
			OutputStream os = blob.setBinaryStream(1);
			os.write(data);
			os.flush();
			os.close();
			return blob;
		}

		public void run() {
			try ( BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); ) {

				while ( true ) {
					System.out.print("Continue using ? (Y/N) ");
					String answer = br.readLine();
					if ( answer.toLowerCase().equals("n")) break;
					
					insertData(br);
					queryData(br);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}

		private void queryData(BufferedReader br) throws IOException, ParseException {
			while (true) {
				System.out.print("Continue query ? (Y/N) ");
				String answer = br.readLine();
				if ( answer.toLowerCase().equals("n")) break;
				Date datum = getDateFromConsole(br);
				
				System.out.print("Part: ");
				int part = Integer.parseInt(br.readLine());

				byte[] data = queryDataFor(datum,part);
				System.out.println("Byte array: " + Arrays.toString(data));

			}
		}

		private byte[] queryDataFor(Date datum, int part) {
			String sql = "select hll_bytes from part_all where datum = ? AND part = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setDate(1, datum);
				ps.setInt(2, part);
				ResultSet rs = ps.executeQuery();				
				while ( rs.next() ) {
					Blob blob = rs.getBlob(1);
					InputStream is = blob.getBinaryStream();
					byte[] bytes = Helpers.toByteArray(is);
					return bytes;
				}
				return null;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		private java.sql.Date getDateFromConsole(BufferedReader br) throws IOException, ParseException {
			System.out.print("Date (yyyy-mm-dd): " );
			String line = br.readLine().trim();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsedDate = dateFormat.parse(line);
			return new java.sql.Date(parsedDate.getTime());
		}

		private void insertData(BufferedReader br) throws IOException, ParseException {
			while ( true ) {

				System.out.print("Continue input ? (Y/N) ");
				String answer = br.readLine();
				if ( answer.toLowerCase().equals("n")) break;

				Date datum = getDateFromConsole(br);
				
				System.out.print("Part: ");
				int part = Integer.parseInt(br.readLine());
				
				System.out.print("Data: ");
				byte[] data = parseByteArrayFrom(br);
				insertOrUpdate(datum, part, data);
			}
		}

		private byte[] parseByteArrayFrom(BufferedReader br) throws IOException {
			byte[] res = null;
			String line = br.readLine();
			String[] words = line.split(",");
			res = new byte[words.length];
			for(int i = 0 ; i < words.length ; i++) {
				res[i] = Byte.parseByte(words[i]);
			}
			return res;
		}
	}

	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.run();
	}

}
