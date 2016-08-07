package crawler.demo.goodcrawler.sbs.util;

public class UrlUtils {
	/**
	 * @param url
	 * @return
	 * @desc ��Url����ȡ����
	 */
	public String getDomain(String url){
		int domainStartIdx = url.indexOf("//") + 2;
		int domainEndIdx = url.indexOf('/', domainStartIdx);
		return url.substring(domainStartIdx, domainEndIdx);
	}
	/**
	 * @param url
	 * @return
	 * @desc ��Url��ȡ��base url
	 */
	public String getBaseUrl(String url){
		return url.substring(0, url.lastIndexOf("/")+1);
	}

}
