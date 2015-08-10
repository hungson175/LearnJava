package phs.learn.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class LearnReflect {
	
	static private HashMap<Class, Class[]> parameters  = new HashMap<Class, Class[]>();
	static {
		parameters.put(ARequest.class,new Class[] {String.class,String.class,String.class});
		parameters.put(BRequest.class,new Class[] {String.class,String.class});
	}
	public static void main(String[] args) {
		Class clazz = BRequest.class;
//		String[] words = {"Son","31","Male"};		
		String[] words = {"Son","31"};		
		try {
			Constructor<Request> c = clazz.getDeclaredConstructor(parameters.get(clazz));
			Request instance = c.newInstance(words);
			instance.print();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
