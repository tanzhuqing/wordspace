package crawler.demo.goodcrawler.sbs.util;

public class CheckIfUniqueUrlByBloomfilter  implements CheckIfUniqueUrl{

	private static BloomFilter<String> bloomFilter = new BloomFilter<String>(0.001, 1024*1024);
	
	private CheckIfUniqueUrlByBloomfilter instance = null;
	private CheckIfUniqueUrlByBloomfilter(){}
	
	
	public CheckIfUniqueUrlByBloomfilter getInstance() {
		if (instance == null) {
			instance = new CheckIfUniqueUrlByBloomfilter();
		}
		return instance;
	}
	public boolean isDuplicate(String url) {
		boolean b = bloomFilter.contains(url);
		if (!b) {
			bloomFilter.add(url);
		}
		return b;
	}

}
