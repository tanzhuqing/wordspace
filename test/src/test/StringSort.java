package test;

import java.util.ArrayList;
import java.util.List;

public class StringSort {
	
	public static List<String>  strSort(String s) {
		List<String> strlist = new ArrayList<>();
		if (s.length()<=1) {
			strlist.add(s);
			return strlist;
		}
		for (int i = 0; i < s.length(); i++) {
			List<String> subChar = strSort(removeChar(s,i));
			System.out.println(subChar);
			for (int j = 0; j < subChar.size(); j++) {
				String str = s.substring(i, i+1)+subChar.get(j);
				System.out.println(str);
				strlist.add(str);
			}
		}
		return strlist;
	}
	
	public static String removeChar(String s,int index) {
		String string = s.substring(0, index)+s.substring(index+1, s.length());
		System.out.println(string);
		return string;
	}

	
	public static void main(String[] args) {
		String str = "abc";
		List<String> list = strSort(str);
		System.out.println(list);
	}
}
