package crawler.demo.goodcrawler.sbs.url;

import java.io.Serializable;

public class WebURL  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String url;
	private String jobName;
	private int docid;
	private int parentDocid;
	private String parentUrl;
	private short depth;
	private String domain;
	private String subDomain;
	private String path;
	private String anchor;
	private byte priority;
	boolean recraw = false;
	
	@Override
	public int hashCode(){
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		WebURL otherUrl = (WebURL)o;
		return url != null && url.equals(otherUrl.getUrl());
	}
	
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		try {
			this.url = url;
			int domainStartIdx = url.indexOf("//") + 2;
			if (domainStartIdx < 0) {
				domainStartIdx = 0;
			}
			int domainEndIdx = url.indexOf('/', domainStartIdx);
			if (domainEndIdx < domainStartIdx) {
				domainEndIdx = url.length();
			}
			domain = url.substring(domainStartIdx, domainEndIdx);
			subDomain = "";
			String[] parts = domain.split("\\.");
			if (parts.length > 2) {
				domain = parts[parts.length - 2] + "." +parts[parts.length - 1];
				int limit = 2;
				if (TLDList.getInstance().contains(domain)) {
					domain = parts[parts.length - 3] + "." + domain;
					limit = 3;
				}
				for (int i = 0; i < parts.length - limit; i++) {
					if (subDomain.length() > 0) {
						subDomain += ".";
					}
					subDomain += parts[i];
				}
				
			}
			path = url.substring(domainEndIdx);
			int pathEndIdx = path.indexOf('?');
			if (pathEndIdx >= 0) {
				path = path.substring(0, pathEndIdx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getDocid() {
		return docid;
	}
	public void setDocid(int docid) {
		this.docid = docid;
	}
	public int getParentDocid() {
		return parentDocid;
	}
	public void setParentDocid(int parentDocid) {
		this.parentDocid = parentDocid;
	}
	public String getParentUrl() {
		return parentUrl;
	}
	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}
	public short getDepth() {
		return depth;
	}
	public void setDepth(short depth) {
		this.depth = depth;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSubDomain() {
		return subDomain;
	}
	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public byte getPriority() {
		return priority;
	}
	public void setPriority(byte priority) {
		this.priority = priority;
	}
	public boolean isRecraw() {
		return recraw;
	}
	public void setRecraw(boolean recraw) {
		this.recraw = recraw;
	}
	
	

}
