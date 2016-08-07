package crawler.demo.goodcrawler.sbs.util;

import crawler.demo.goodcrawler.sbs.url.UrlSignatureSet;

public class CheckIfUniqueUrlByMd5 implements CheckIfUniqueUrl {

	public boolean isDuplicate(String url) {
		
		return UrlSignatureSet.duplicate(url);
	}
 
	
}
