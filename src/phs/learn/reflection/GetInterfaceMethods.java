package phs.learn.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;

public class GetInterfaceMethods {
	/**
	 * Input: string[] words, an interface with method
	 * Output: prove that className.process() is called ( and parameters must be shown)
	 * @param args
	 */
	public static void main(String[] args) {
		Class iExample = IExample.class;
		Method[] declaredMethods = iExample.getDeclaredMethods();
		String[][] input = {
				{"aMethod", "RnD" ,"WAD" , "Vng"},
				{"bMethod", "2015,13,23", "year"},
		};
		for (String[] p : input) {
			String className = GetInterfaceMethods.class.getPackage().getName() +"."+ upperFirstChar(p[0]) + "Processor";
			try {
				Class<?> classProcessor = Class.forName(className);
				Constructor<?>[] ctors = classProcessor.getDeclaredConstructors();
				Assert.assertEquals(1,ctors.length);			
				Constructor constructor = ctors[0];
				Processor processor = (Processor) constructor.newInstance(getTails(p));
				processor.process();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static String[] getTails(String[] p) {
		if ( p.length == 0 ) return null;
		String[] rs = new String[p.length-1];
		for(int i = 1 ; i < p.length ; i++)
			rs[i-1] = p[i]; 
		return rs;
	}

	public static String upperFirstChar(String s) {
		char[] c = s.toCharArray();
		c[0] = Character.toUpperCase(c[0]);
		return new String(c);
	}
}
