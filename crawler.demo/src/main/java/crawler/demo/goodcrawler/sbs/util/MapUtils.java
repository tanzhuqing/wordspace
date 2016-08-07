package crawler.demo.goodcrawler.sbs.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MapUtils {
	/**
	 * �ϲ�m2��m1
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap<String, Object> mager(HashMap<String, Object> m1,
			HashMap<String, Object> m2) {
		if (null != m1 && null != m2) {
			// ����m2
			Set<String> keyset = m2.keySet();
			for (String key : keyset) {
				Object obj1 = m1.get(key);
				Object obj2 = m2.get(key);
				if (obj2 instanceof Map && obj1 instanceof Map) {
					((Map) obj1).putAll((Map) obj2);
				} else if (obj2 instanceof List && obj1 instanceof List) {
					((List) obj1).addAll((List) obj2);
				} else if (obj2 instanceof Set && obj1 instanceof Set) {
					((Set)obj1).addAll((Set)obj2);
				}else {
					if (null != obj2) {
						m1.put(key, obj2);
					}
				}
			}
		}
		return m1;
	}
	
	public static void main(String[] args) {
		System.out.println((null instanceof Object));
		HashMap<String, Object> m1 = Maps.newHashMap();
		m1.put("a", "1");
		m1.put("b", "x");
		m1.put("d", Lists.newArrayList(1,2,34));
		HashMap<String, Object> m2 = Maps.newHashMap();
		m2.put("a", "x");
		m2.put("b", "y");
		m2.put("c", "3");
		m1.put("d", Lists.newArrayList(1,2,34,5));
		m1.put("e", Maps.newHashMap());
		System.out.println(mager(m1, m2));
		System.out.println(m1);
	}
}
