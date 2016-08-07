package crawler.demo.goodcrawler.sbs.util.download;

import org.apache.commons.lang3.StringUtils;

public class DownloadInfo {
	  /**
     * 下载文件url
     */
    private String url;
    /**
     * 下载文件名称
     */
    private String fileName;
    /**
     * 下载文件路径
     */
    private String filePath;
    /**
     * 将文件分为blockNum个块进行下载
     */
    private int blockNum;
    
    /*
     * 下载文件默认保存路径
     */
    private final static String FILE_PATH = "temp";
    /*
     * 默认分块数、线程数
     */
    private final static int SPLITTER_NUM = 5;
    
    public DownloadInfo() {
        super();
    }
    
    /**
     * @param url 下载地址
     */
    public DownloadInfo(String url) {
        this(url, null, null, SPLITTER_NUM);
    }
    
    /**
     * @param url 下载地址url
     * @param splitter 分成多少段或是多少个线程下载
     */
    public DownloadInfo(String url, int splitter) {
        this(url, null, null, splitter);
    }
    
    /***
     * @param url 下载地址
     * @param fileName 文件名称
     * @param filePath 文件保存路径
     * @param splitter 分成多少段或是多少个线程下载
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
     * 通过url获得文件名称
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
