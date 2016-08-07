package crawler.demo.goodcrawler.sbs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.bcel.generic.NEW;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.Header;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import crawler.demo.goodcrawler.sbs.fetcher.CustomFetchStatus;
import crawler.demo.goodcrawler.sbs.page.PageFetchResult;
import crawler.demo.goodcrawler.sbs.url.URLCanonicalizer;


public class BinaryDateDwonLoader {
	protected final Object mutex = new Object();
	protected static final Logger logger = Logger.getLogger(BinaryDateDwonLoader.class);
	protected DefaultHttpClient httpClient;
	protected long lastFetchTime = 0;
	protected int delayTime = 200;
	protected int maxSize = 1024*1024*5;
	
	private BinaryDateDwonLoader(){
		httpClient = new DefaultHttpClient();
	}
	
	private static BinaryDateDwonLoader dwonLoader = null;
	
	public static BinaryDateDwonLoader getInstance() {
		if (dwonLoader == null) {
			dwonLoader = new BinaryDateDwonLoader();
		}
		return dwonLoader;
	}
	
	private PageFetchResult fetchHeader(String webUrl) {
		PageFetchResult fetchResult = new PageFetchResult();
		String toFetchURL = webUrl;
		HttpGet get = null;
		try {
			get = new HttpGet(toFetchURL);
			synchronized (mutex) {
				long now = (new Date()).getTime();
				if (now - lastFetchTime < delayTime) {
					Thread.sleep(delayTime - (now - lastFetchTime));
				}
				lastFetchTime = (new Date()).getTime();
			}
			get.addHeader("Accepte-Encoding","gzip");
			HttpResponse response = httpClient.execute(get);
			fetchResult.setEntity(response.getEntity());
			fetchResult.setResponseHeaders(response.getAllHeaders());
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode != HttpStatus.SC_NOT_FOUND) {
					if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						Header header = response.getFirstHeader("Location");
						if (header != null) {
							String movedToUrl = header.getValue();
							movedToUrl = URLCanonicalizer.getCanonicalURL(movedToUrl, toFetchURL);
							fetchResult.setMovedToUrl(movedToUrl);
						}
						fetchResult.setStatusCode(statusCode);
						return fetchResult;
					}
					logger.info("Failed : "+response.getStatusLine().toString()+", while fetching " + toFetchURL);
				}
				
				fetchResult.setStatusCode(response.getStatusLine().getStatusCode());
				response.getEntity().getContent().close();
				return fetchResult;
			}
			
			fetchResult.setFetchUrl(toFetchURL);
			String uri = get.getURI().toString();
			if (!uri.equals(toFetchURL)) {
				if (!URLCanonicalizer.getCanonicalURL(uri).equals(toFetchURL)) {
					fetchResult.setFetchUrl(uri);
				}
			}
			
			if (fetchResult.getEntity() != null) {
				long size = fetchResult.getEntity().getContentLength();
				if (size == -1) {
					Header length = response.getLastHeader("Content-Length");
					if (length == null) {
						length = response.getLastHeader("Content-Length");
					}
					if (length != null) {
						size = Integer.parseInt(length.getValue());
					}else {
						size = -1;
					}
				}
				if (size > maxSize) {
					fetchResult.setStatusCode(CustomFetchStatus.PageTooBig);
					get.abort();
					return fetchResult;
				}
				
				fetchResult.setStatusCode(HttpStatus.SC_OK);
				return fetchResult;
			}
			get.abort();
			
		} catch (IOException e) {
			logger.error("Fatal transport error : "+e.getMessage() + " while fetching "+toFetchURL);
			fetchResult.setStatusCode(CustomFetchStatus.FatalTransportError);
			return fetchResult;
		}catch (IllegalStateException e) {
			// TODO: handle exception
		}catch (Exception e) {
			if (e.getMessage() == null) {
				logger.error("Error while fetching " + toFetchURL);
			}else {
				logger.error(e.getMessage() + "while fetching " + toFetchURL);
			}
		}finally{
			try {
				if (fetchResult.getEntity() == null && get != null) {
					get.abort();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		fetchResult.setStatusCode(CustomFetchStatus.UnknownError);
		return fetchResult;
	}
	
	public String downLoad(String url, String distPath) {
		PageFetchResult result = fetchHeader(url);
		if (null != result && null != result.getEntity()) {
			String fileName = EncryptUtils.encodeMD5(url);
			File path = new File(distPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			String file = distPath + File.separator + fileName + url.substring(url.lastIndexOf('.'));
			File imgFile = new File(file);
			try {
				OutputStream outputStream = new FileOutputStream(imgFile);
				result.getEntity().writeTo(outputStream);
				outputStream.close();
				Thumbnails.of(file).width(200).outputQuality(0.6f).toFile(file);
				return imgFile.getName();
			} catch (FileNotFoundException e) {
				logger.warn(e.getMessage());
			}catch (IOException e) {
				logger.warn(e.getMessage());
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String img = "";
		BinaryDateDwonLoader dateDwonLoader = BinaryDateDwonLoader.getInstance();
		PageFetchResult result = dateDwonLoader.fetchHeader(img);
		try {
			OutputStream outputStream = new FileOutputStream(new File("d:\\" + img.substring(img.lastIndexOf('/')+1)));
			result.getEntity().writeTo(outputStream);
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
