package phs.learn.reflection;

public class ARequest extends Request {

	String name;
	String age;
	String gender;
	
//	public ARequest(String[] words) {
//		name = words[0];
//		age = words[1];
//		gender = words[2];
//	}
	public ARequest(String name,String age,String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
	@Override
	public void print() {
		System.out.println("NAme = " + name + " / age = " + age + " / gender = " + gender);
	}

}
