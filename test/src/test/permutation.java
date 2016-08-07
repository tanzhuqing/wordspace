package test;

import java.util.List;

public class permutation {
   
	public static void process(List<String> list,int begin,int end) {
		if (begin == end) {
			for (String s : list) {
				System.out.print(s+" ");
			}
			System.out.println();
		}else {
			for (int i = begin; i <= end; i++) {
				swap(list,i,begin);
				process(list, begin+1, end);
				swap(list,i,begin);
			}
		}
	}
}
