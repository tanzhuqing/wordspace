package crawler.demo.goodcrawler.sbs.page;

import java.io.EOFException;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.Header;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class PageFetchResult {

	protected static final Logger logger = Logger.getLogger(PageFetchResult.class);
	
	protected int statusCode;
	protected HttpEntity entity = null;
	protected Header[] responseHeaders = null;
	protected String fetchUrl = null;
	protected String movedToUrl = null;
	
	public boolean fetchContent(Page page) {
		try {
			page.load(entity);
			page.setFetchResponseHeaders(responseHeaders);//setFetchResponseHeaders(responseHeaders);
			return true;
		} catch (Exception e) {
			logger.info("Exception while fetching content for : "+page.getWebURL().getUrl()+"["+e.getMessage()+"]");
	
		}
		return false;
	}
	
	public void discardContentIfNotConsumed() {
		try {
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		} catch (EOFException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public HttpEntity getEntity() {
		return entity;
	}
	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}
	public Header[] getResponseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(Header[] responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	public String getFetchUrl() {
		return fetchUrl;
	}
	public void setFetchUrl(String fetchUrl) {
		this.fetchUrl = fetchUrl;
	}
	public String getMovedToUrl() {
		return movedToUrl;
	}
	public void setMovedToUrl(String movedToUrl) {
		this.movedToUrl = movedToUrl;
	}
	
	
}
