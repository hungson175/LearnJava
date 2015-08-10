
package phs.learn.reflection;

public class BRequest extends Request {
	String name;
	String age;
	public BRequest(String[] words) {
		name = words[0];
		age  = words[1];
	}
	
	public BRequest(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public void print() {
		System.out.println("name = " + name + " / age = " + age);
	}
	
	
}
