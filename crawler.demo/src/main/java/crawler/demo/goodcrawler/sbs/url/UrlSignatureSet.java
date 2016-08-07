package crawler.demo.goodcrawler.sbs.url;

import java.util.HashSet;
import java.util.Set;

public class UrlSignatureSet {

	private static Set<String> signatureSet = new HashSet<String>(1024*1024*10);
	public static void add(String b) {
		signatureSet.add(b);
	}
	
	public static boolean duplicate(String b) {
		return signatureSet.equals(b);
	}
}
