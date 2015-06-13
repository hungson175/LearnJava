package phs.learn.regex;

import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHarness {
	public static void main(String[] args) {
		Console console = System.console();
		if ( console == null ) {
			return;
		}
		while ( true ) {
			String regex = console.readLine("%nEnter your regex:");
			if ( regex.toLowerCase().equals(""));
			Pattern pattern = Pattern.compile(regex);
			String text = console.readLine("Enter input string to search: ");
			Matcher matcher = pattern.matcher(text);
			boolean found = false;
			while ( matcher.find() ) {
				console.format("Text found: " + " \"%s\" at [%d,%d)%m", 
						matcher.group(),
						matcher.start(),
						matcher.end());
				found = true;
			}
			if ( ! found ) {
				console.format("No match found.%n");
			}
		}
	}
}
