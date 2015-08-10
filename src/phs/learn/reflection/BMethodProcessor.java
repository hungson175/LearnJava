package phs.learn.reflection;

import java.util.ArrayList;

public class BMethodProcessor implements Processor {
	private String year;
	private ArrayList<Integer> numberList;
	public BMethodProcessor(String numberList, String year) {
		this.numberList = parseList(numberList);
		this.year = year;
	}
	private ArrayList<Integer> parseList(String numberListString) {
		ArrayList<Integer> rs = new ArrayList<Integer>();
		String[] numbers = numberListString.split(",");
		for (String n : numbers) {
			rs.add(Integer.parseInt(n));
		}
		return rs ;
	}
	@Override
	public void process() {
		System.out.println("BMethod sum " + getSum() + " year " + year);
	}
	private int getSum() {
		int s = 0;
		for (int n : numberList) {
			s += n;
		}
		return s;
	}

}
