package crawler.demo.goodcrawler.sbs.util.download;

import org.apache.commons.lang3.StringUtils;

public class DownloadInfo {
	  /**
     * �����ļ�url
     */
    private String url;
    /**
     * �����ļ�����
     */
    private String fileName;
    /**
     * �����ļ�·��
     */
    private String filePath;
    /**
     * ���ļ���ΪblockNum�����������
     */
    private int blockNum;
    
    /*
     * �����ļ�Ĭ�ϱ���·��
     */
    private final static String FILE_PATH = "temp";
    /*
     * Ĭ�Ϸֿ������߳���
     */
    private final static int SPLITTER_NUM = 5;
    
    public DownloadInfo() {
        super();
    }
    
    /**
     * @param url ���ص�ַ
     */
    public DownloadInfo(String url) {
        this(url, null, null, SPLITTER_NUM);
    }
    
    /**
     * @param url ���ص�ַurl
     * @param splitter �ֳɶ��ٶλ��Ƕ��ٸ��߳�����
     */
    public DownloadInfo(String url, int splitter) {
        this(url, null, null, splitter);
    }
    
    /***
     * @param url ���ص�ַ
     * @param fileName �ļ�����
     * @param filePath �ļ�����·��
     * @param splitter �ֳɶ��ٶλ��Ƕ��ٸ��߳�����
     * @throws DownLoadException 
     */
    public DownloadInfo(String url, String fileName, String filePath, int splitter) {
        super();
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("url is blank!");
        }
        this.url =  url;
        this.fileName = (StringUtils.isBlank(fileName)) ? getFileName(url) : fileName;
        this.filePath = (StringUtils.isBlank(filePath)) ? FILE_PATH : filePath;
        this.blockNum = (splitter < 1) ? SPLITTER_NUM : splitter;
    }
    
    /**
     * ͨ��url����ļ�����
     * @param url
     * @return
     */
    private String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }
    
    public String getUrl() {
        return url;
    }
 
    public void setUrl(String url) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("url is null!");
        }
        this.url = url;
    }
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = (StringUtils.isBlank(fileName)) ? getFileName(url) : fileName;
    }
 
    public String getFilePath() {
        return filePath;
    }
 
    public void setFilePath(String filePath) {
        this.filePath = (StringUtils.isBlank(filePath)) ? FILE_PATH : filePath;
    }
 
    public int getSplitter() {
        return blockNum;
    }
 
    public void setSplitter(int splitter) {
        this.blockNum = (splitter < 1) ? SPLITTER_NUM : splitter;
    }
    
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DownloadInfo [url=").append(url).append(", fileName=")
				.append(fileName).append(", filePath=").append(filePath)
				.append(", splitter=").append(blockNum).append("]");
		return builder.toString();
	}
}
