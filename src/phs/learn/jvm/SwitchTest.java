package phs.learn.jvm;

public class SwitchTest {
	public int doSwitch(String str) {
		switch (str) {
		case "abc": return 1;
		case "123": return 2;
		default: return 0;
		}
	}
}
