package phs.learn.sql;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class MySQLAccess {
	private Connection connection;
	private ResultSet resultSet;

	public MySQLAccess() {
		//Class.forName("com.mysql.jdbc.Driver"); //TODO: try & see if it works
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?"+ 
					"user=root&password=dangthaison");
			cursorHoldabilitySupport(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//	public void readDatabase() {
//		PreparedStatement preparedStatement = null;
//		Statement statement = null;
//		try {
//			
//			statement = connect.createStatement();
//			
//			//get all rows
//			resultSet = statement.executeQuery("select * from feedback.comments");
//			printOutResults(resultSet);
//			
//			//insert a new row, using prepared statement for better performance
//			preparedStatement = connect.prepareStatement("insert into feedback.comments values (default,?,?,?)");
//			preparedStatement.setString(1, "doan.huong");
//			preparedStatement.setString(2,"2015-06-01");
//			preparedStatement.setString(3,"Qua tet Vova dep ko ?");
//			preparedStatement.executeUpdate();
//			preparedStatement.close();
//			
//			//select again
//			preparedStatement = connect.prepareStatement("select * from feedback.comments"); //righty: leaked !!!
//			resultSet = preparedStatement.executeQuery();
//			printOutResults(resultSet);
//			preparedStatement.close();
//			
//			
//			//remove the inserted comment
//			preparedStatement = connect.prepareStatement("delete from feedback.comments where username = ? "); //righty: leaked !!!
//			preparedStatement.setString(1, "hungson175");
//			preparedStatement.executeUpdate();
//			preparedStatement.close();
//			
//			//read again
//			resultSet = statement.executeQuery("select * from feedback.comments");
//			printOutResults(resultSet);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {statement.close();} catch (Exception e) {}
//			try{preparedStatement.close();} catch (Exception e) {}
//		}
//	}
	
	/**
	 * Learn about blob, mysql & hll:
	 * 1. Write & read bytes data from database
	 * 2. Test with 3 files: with each of them	 *  
	 *   -write hll bytes data to db, 
	 *   -retrieve them
	 *  Then: merge them 
	 *  Then: compare to normal hlls without merging
	 *
	 */
	public void writeBytesData(int type,int tid, byte[] data) {
		String sql = "INSERT INTO test.hourly values (default,?,?,?)";
//		String sql = "insert into test.hh values (default,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, type);
			ps.setInt(2, tid);
			Blob blob = connection.createBlob();
			OutputStream os = blob.setBinaryStream(1);
			os.write(data);
			os.flush();
			os.close();
			ps.setBlob(3, blob);
			int naffected = ps.executeUpdate();
			if ( naffected != 1) {
				System.err.println("Cannot write the data");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getBytesData(int id) {
		String sql = "Select hll from test.hourly"; 
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Blob blob = rs.getBlob(1);
				InputStream binaryStream = blob.getBinaryStream();
				byte[] bytes = toByteArray(binaryStream);
				for(int i = 0 ; i < bytes.length ; i++) {
					System.out.print(""+bytes[i]+",");
				}
				System.out.println();
			}
		} catch (SQLException  e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<byte[]> getAllBytesArray() {
		String sql = "Select hll from test.hourly";
		ArrayList<byte[]> allBytes = new ArrayList<byte[]>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();			
			while (rs.next()) {
				Blob blob = rs.getBlob(1);
				InputStream binaryStream = blob.getBinaryStream();
				byte[] bytes = toByteArray(binaryStream);
				allBytes.add(bytes);
			}
			return allBytes;
		} catch (SQLException  e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	private byte[] toByteArray(InputStream is) {
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

	public static void cursorHoldabilitySupport(Connection conn)
			throws SQLException {

		DatabaseMetaData dbMetaData = conn.getMetaData();
		System.out.println("ResultSet.HOLD_CURSORS_OVER_COMMIT = " +
				ResultSet.HOLD_CURSORS_OVER_COMMIT);

		System.out.println("ResultSet.CLOSE_CURSORS_AT_COMMIT = " +
				ResultSet.CLOSE_CURSORS_AT_COMMIT);

		System.out.println("Default cursor holdability: " +
				dbMetaData.getResultSetHoldability());

		System.out.println("Supports HOLD_CURSORS_OVER_COMMIT? " +
				dbMetaData.supportsResultSetHoldability(
						ResultSet.HOLD_CURSORS_OVER_COMMIT));

		System.out.println("Supports CLOSE_CURSORS_AT_COMMIT? " +
				dbMetaData.supportsResultSetHoldability(
						ResultSet.CLOSE_CURSORS_AT_COMMIT));
	}

	private void printOutResults(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			long id = resultSet.getLong("id");
			String username = resultSet.getString("username");
			Date date = resultSet.getDate("date");
			String comment = resultSet.getString("comment");
			String message = String.format("id = %d, username = %s, date = %s, comment = %s", id,username,date,comment);
			System.out.println(message);			
			
		}
	}

	public void clearData() {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("delete from hourly");			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
