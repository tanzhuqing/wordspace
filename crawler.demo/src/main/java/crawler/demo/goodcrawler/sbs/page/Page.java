package crawler.demo.goodcrawler.sbs.page;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import crawler.demo.goodcrawler.sbs.url.WebURL;

public class Page implements Serializable {
	private static final long serialVersionUID = 4218868917032659053L;
    protected Pattern p = Pattern.compile("(?<=charset=)(.+)(?=\")");
    protected WebURL url;
    protected byte[] contentData;
    protected String contentType;
    protected String contentEncoding;
    protected String contentCharset;
    protected Header[] fetchResponseHeaders;
    
    protected ParseData parseData;
    public Page(WebURL url){
    	this.url = url;
    }
    public WebURL getWebURL() {
		return url;
	}
    public void setWebURL(WebURL url) {
		this.url = url;
	}
    
    
    public void  load(HttpEntity entity)throws Exception {
		contentType = null;
		Header type = entity.getContentType();
		if (type != null) {
			contentType = type.getValue();
		}
		contentEncoding = null;
		Header encoding = entity.getContentEncoding();
		if (encoding != null) {
			contentEncoding = encoding.getValue();
		}
		
		Charset charset = ContentType.getOrDefault(entity).getCharset();
		if (charset != null) {
			contentCharset = charset.displayName();
		}
		contentData = EntityUtils.toByteArray(entity);
		if (null == contentCharset || "" == contentCharset) {
			int length = contentData.length;
			if (length > 2048) {
				length = 2048;
			}
			String html = new String(contentData,0,2048);
			contentCharset = matchCharset(html.toLowerCase());
		}
	}
    
    public String matchCharset(String content) {
		Matcher matcher = p.matcher(content);
		if (matcher.find()) {
			return matcher.group();
		}
		return "gb2312";
	}
    
    public Header[] getFetchResponseHeaders() {
		return fetchResponseHeaders;
	}
	public WebURL getUrl() {
		return url;
	}
	public void setUrl(WebURL url) {
		this.url = url;
	}
	public byte[] getContentData() {
		return contentData;
	}
	public void setContentData(byte[] contentData) {
		this.contentData = contentData;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentEncoding() {
		return contentEncoding;
	}
	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}
	public String getContentCharset() {
		return contentCharset;
	}
	public void setContentCharset(String contentCharset) {
		this.contentCharset = contentCharset;
	}

	public void setFetchResponseHeaders(Header[] fetchResponseHeaders) {
		this.fetchResponseHeaders = fetchResponseHeaders;
	}
    
}
