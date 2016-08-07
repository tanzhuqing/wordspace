package crawler.demo.goodcrawler.sbs.page;

import java.io.Serializable;
import java.util.HashMap;

import crawler.demo.goodcrawler.sbs.url.WebURL;

public class ExtractedPage<V, T> implements Serializable {
	private static final long serialVersionUID = 3965488944964575676L;
	/**
	 * �����ֶ�
	 */
	String reserve;
	/**
	 * ���ӵ�WebURL��Ϣ
	 */
	WebURL url;
	/**
	 * ��ȡ����Ϣ����job�����е�select��Ӧ�����ݿ�������չ�ӿ��������޸ĺͶ��塣
	 */
	HashMap<Object, Object> messages = new HashMap<>();

	ExtractResult result;

	public ExtractResult getResult() {
		return result;
	}

	public ExtractedPage<V, T> setResult(ExtractResult result) {
		this.result = result;
		return this;
	}

	public ExtractedPage() {
	}

	public ExtractedPage(WebURL url) {
		super();
		this.url = url;
	}

	public WebURL getUrl() {
		return url;
	}

	public ExtractedPage<V, T> setUrl(WebURL url) {
		this.url = url;
		return this;
	}

	public String getReserve() {
		return reserve;
	}

	public ExtractedPage<V, T> setReserve(String reserve) {
		this.reserve = reserve;
		return this;
	}

	public HashMap getMessages() {
		return messages;
	}

	@SuppressWarnings("unchecked")
	public ExtractedPage<V, T> setMessages(HashMap messages) {
		this.messages = messages;
		return this;
	}
	
	public void setMessage(String k,Object v){
		this.messages.put(k,v);
	}

	public ExtractedPage<V, T> addMessage(V key, T value) {
		messages.put(key, value);
		return this;
	}
}
