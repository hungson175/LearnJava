package phs.learn.reflection;

public class AMethodProcessor implements Processor {
	private String s3;
	private String s2;
	private String s1;
	public AMethodProcessor(String s1, String s2, String s3) {
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
	}
	@Override
	public void process() {
		System.out.println("AMethod s1 " + s1 + " s2 " + s2 + " s3 " + s3);
	}

}
