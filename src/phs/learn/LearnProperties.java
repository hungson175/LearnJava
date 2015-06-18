package phs.learn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LearnProperties {
	public static void main(String[] args) {
		 
		Properties prop = new Properties();
		InputStream input = null;
		try {
	 
			input = new FileInputStream("config.properties");
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value and print it out
			System.out.println(prop.getProperty("db.username"));
			System.out.println(prop.getProperty("db.password"));
			System.out.println(prop.getProperty("db.user"));
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
