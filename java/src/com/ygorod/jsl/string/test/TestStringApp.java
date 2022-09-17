package com.ygorod.jsl.string.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.ygorod.jsl.string.TestString;

public class TestStringApp {

	public static void main(String[] args) {
		int max = 10;
		int min = 1;
		long startTimeMillis;
		long stopTimeMillis;

		startTimeMillis = System.currentTimeMillis();
		TestString ts = new TestString();
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 15000; i++) {
			int len = (int) ((Math.random() * (max - min)) + min);
			list.add(ts.generateRandomString(len));
			// System.out.println(i + "\t" + len + " : " + ts.generateRandomString(len));
		}

		list.add("Java");
		list.add("Kotlin");
		list.add("Clojure");
		list.add("Groovy");
		list.add("Scala");
		stopTimeMillis = System.currentTimeMillis();
		System.out.println("String list generated. Run Time: " + (stopTimeMillis - startTimeMillis) + " msec");
		
		List<String> result = new ArrayList<>();
		
		
		result.clear();
		startTimeMillis = System.currentTimeMillis();
		for (String s : list) {
			if (s.indexOf("ava") != -1 ) {
				result.add(s);				
			}
		}
		stopTimeMillis = System.currentTimeMillis();
		System.out.println("indexOf() search Ok ("+ list.size() +" items): "+ result.size() + " search string found");
		System.out.println(result);
		System.out.println("indexOf() search done. Run Time: " + (stopTimeMillis - startTimeMillis) + " msec");
		
		
		result.clear();
		startTimeMillis = System.currentTimeMillis();
		for (String s : list) {
			if (s.contains("ava")) {
				result.add(s);				
			}
		}
		stopTimeMillis = System.currentTimeMillis();
		System.out.println("contains() search Ok ("+ list.size() +" items): "+ result.size() + " search string found");
		System.out.println(result);
		System.out.println("contain() search done. Run Time: " + (stopTimeMillis - startTimeMillis) + " msec");
	
		
		result.clear();
		startTimeMillis = System.currentTimeMillis();
    	result = list.stream().filter(x -> x.contains("ava")).collect(Collectors.toList());
    	stopTimeMillis = System.currentTimeMillis();    	
		System.out.println("filter() search Ok ("+ list.size() +" items): "+ result.size() + " search string found");   	
		System.out.println(result);
		System.out.println("filter() search done. Run Time: " + (stopTimeMillis - startTimeMillis) + " msec");
		
		
		String str = "123455.2.2";
		str = str.substring(0, str.indexOf("."));
		System.out.println(str);
	}

}
