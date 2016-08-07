package crawler.demo.goodcrawler.sbs.util.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;

import crawler.demo.goodcrawler.sbs.crawler.conf.PropertyConfigurationHelper;
import crawler.demo.goodcrawler.sbs.crawler.extractor.selector.exception.DownLoadException;
import crawler.demo.goodcrawler.sbs.util.image.ImageResize;

public class MultiThreadDownload {

	private static final Log log = LogFactory.getLog(MultiThreadDownload.class);
	private static final String down_error_log_file = PropertyConfigurationHelper.getInstance().getString("status.save.path.down.error", "status/down-error.log");
	/**
	 * ��������С
	 */
	private static int BUFFER_SIZE = 1024*1024;
	/**
	 * �ֿ��С
	 */
	private long blockSize = 1024*1024;
	/**
	 *  �粻����blockSize����������threadNum����������threadNum��blockSize��������
	 */
	private int threadNum = 3;
	/**
	 * ���ӳ�ʱʱ��
	 */
	private int setConnectTimeout = 10000;
	/**
	 * ��ȡ��ʱʱ��
	 */
	private int setReadTimeout = 10000;
	/**
	 * �Ѿ����صĳ���
	 */
	private long downLen = -1;
	/**
	 * �ļ��ܳ���
	 */
	private long contentLen = 0;
	/**
	 * ��ʼ���ص�ʱ��
	 */
	private Date date ;
	/**
	 * ִ�����ص��̳߳�
	 */
	private DownLoadPool pool = DownLoadPool.getInstance();
	/**
	 * ��ʽ���ٷֱ�
	 */
	private DecimalFormat decimalFormat = new DecimalFormat("##.##%");
	/**
	 * �Ƿ��������
	 */
	private boolean finished = false;
	/**
	 * lock
	 */
	private final Object object = new Object();
	
	public MultiThreadDownload(int threadNum,
			int setConnectTimeout, int setReadTimeout) {
		super();
		this.threadNum = threadNum;
		this.setConnectTimeout = setConnectTimeout;
		this.setReadTimeout = setReadTimeout;
	}
	
	public MultiThreadDownload(long blockSize) {
		super();
		this.blockSize = blockSize;
		this.threadNum = 0;
	}
	
	public MultiThreadDownload(int threadNum) {
		super();
		this.threadNum = threadNum;
		this.blockSize = 0;
	}
	
	public MultiThreadDownload(){}
	
	public long getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public int getSetConnectTimeout() {
		return setConnectTimeout;
	}

	public void setSetConnectTimeout(int setConnectTimeout) {
		this.setConnectTimeout = setConnectTimeout;
	}

	public int getSetReadTimeout() {
		return setReadTimeout;
	}

	public void setSetReadTimeout(int setReadTimeout) {
		this.setReadTimeout = setReadTimeout;
	}

	public synchronized long addLen(int downSize){
		this.downLen = this.downLen + downSize;
		synchronized (object) {
        	object.notify();
		}
		return this.downLen;
	}
	/**
	 * ���������ļ����첽��
	 * @param url
	 * @param path
	 * @param fileName
	 */
	@SuppressWarnings("rawtypes")
	public void downFile(final URL url,final String path,final String fileName,boolean listen) throws DownLoadException {
		Callable callable = new Callable() {

			public Future call() throws Exception {
				downLoad(url,path,fileName);
				return null;
			}
			
		};
		pool.submit(callable);
		if (listen) {
			listenStatus();
		}
	}
	/**
	 * ����һ��ͼƬȻ��ѹ�����첽����
	 * @param url
	 * @param path
	 * @param fileName
	 * @param listen
	 * @throws DownLoadException
	 */
	@SuppressWarnings("rawtypes")
	public void downImageThenResizeAsyn(final URL url,final String path,final String fileName,final int width,final float quality) throws DownLoadException{
		Callable callable = new Callable() {
			
			public Future call() throws Exception {
				File img = downLoad(url,path,fileName);
				ImageResize imageResize = new ImageResize();
				imageResize.resizeAsynchronous(img, width, quality, true);
				return null;
			}
		};
		pool.submit(callable);
	}
	
	/**
	 * �첽����ѹ��
	 * @param url
	 * @param path
	 * @param fileName
	 * @param width
	 * @param height
	 * @param quality
	 * @param del
	 * @throws DownLoadException
	 */
	@SuppressWarnings("rawtypes")
	public void downImageThenResizeAsyn(final URL url,final String path,final String fileName,final int width,final float quality,final boolean del) throws DownLoadException{
		Callable callable = new Callable() {
			
			public Future call() throws Exception {
				File img = downLoad(url,path,fileName);
				ImageResize imageResize = new ImageResize();
				imageResize.resizeAsynchronous(img, width ,quality, del);
				return null;
			}
		};
		pool.submit(callable);
	}
	
	/**
	 * ͬ������ѹ��ͼƬ
	 * @param url
	 * @param path
	 * @param fileName
	 * @param width
	 * @param quality
	 * @throws DownLoadException
	 */
	public void downImageThenResizeSyn(final URL url,final String path,final String fileName,final int width,final float quality) throws DownLoadException{
		File img = downLoad(url,path,fileName);
		ImageResize imageResize = new ImageResize();
		imageResize.resizeAsynchronous(img, width, quality, true);
	}
	/**
	 * �����ļ�
	 * @param url
	 * @param path
	 * @param fileName
	 * @return
	 * @throws DownLoadException
	 */
	@SuppressWarnings("unchecked")
	public File downLoad(final URL url,String path,String fileName) throws DownLoadException{
		// ����״̬
	   this.downLen = -1;
	   this.contentLen = 0;
	   this.date = new Date();
	   this.finished = false;
	   try {
		URLConnection con = url.openConnection();
		//�����Դ����
		this.contentLen = con.getContentLength();
		//����ļ���
		if (StringUtils.isBlank(fileName)) {
			fileName = StringUtils.substringAfter(url.getPath(), "/");			
		}
		//���Ŀ¼
		File _path = new File(path);
		if (!_path.exists()) {
			_path.mkdirs();
		}
		final File file = new File(path+File.separator+fileName);
		if (file.exists()) {
			file.delete();
		}
		if (this.threadNum == 0 && this.blockSize > 0) {
			this.threadNum = (int)(contentLen/blockSize);
			if (this.threadNum == 0) {
				this.threadNum = 1;
			}
		}
		
		long subLen = contentLen / threadNum;
		List<Future<DownLoadBean>> result = Lists.newArrayList();
		for (int i = 0; i < threadNum; i++) {
			final int pos = (int) (i*subLen);
			final int end = (int) ((i+1)*subLen) - 1;
			final int current = pos;
			
			Future<DownLoadBean> f = (Future<DownLoadBean>)pool.submit(new Callable<DownLoadBean>() {
			        int $pos = pos;
			        int $end = end;
			        int $current = current;
			        
			        public DownLoadBean call() throws DownLoadException {
						BufferedInputStream bis = null;
						RandomAccessFile fos = null;
						byte[] buf = new byte[BUFFER_SIZE];
						URLConnection con = null;
						try {
							con = url.openConnection();
							con.setAllowUserInteraction(true);
							con.setRequestProperty("Range", "bytes="+$pos + "-"+ $end);;
							fos = new RandomAccessFile(file, "rw");
							fos.seek($pos);
							
							bis = new BufferedInputStream(con.getInputStream());
							while ($current < $end) {
								int len = bis.read(buf,0,BUFFER_SIZE);
								if (len == -1) {
									break;
								}
								fos.write(buf,0,len);
								$current = $current + len;
								if ($current - 1 > $end) {
									throw new DownLoadException("�������ݳ�����ǰλ�ó�������λ��");
								}
								addLen(len);
							}
							bis.close();
							fos.close();
						} catch (IOException ex) {
							throw new RuntimeException(ex);
						}
						log.debug(this.hashCode()+":������ɿ�["+$pos+","+$end+"]");
						return new DownLoadBean($pos, $end, $current);
					}
			});
			result.add(f);
		}
		Long resultTotal = 0L;
		for(Future<DownLoadBean> f:result){
			DownLoadBean dInfo = f.get();
			resultTotal += dInfo.getCurrent() - dInfo.getPos() ;
		}
		// ���ж��ļ��Ƿ���������
		if(contentLen>resultTotal+1){
			// ��¼���ش�������ӣ��Ӷ����Դ��лָ�
			FileUtils.write(new File(down_error_log_file), url.toString()+"\n", true);
			throw new DownLoadException("�ļ����ز�����");
		}else {
			finished = true;
			return file;
		}
		
	} catch (IOException | InterruptedException | ExecutionException e) {
		// ��¼���ش��������
		try {
			FileUtils.write(new File(down_error_log_file), url.toString()+"\n", true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();
	}
	   return null;
	}
	
	/**
	 * ��������ӡ����״̬
	 */
	private void listenStatus(){
		new Runnable() {
			
			public void run() {
				try {
					synchronized (object) {
						while(true){
							Thread.sleep(1000L);
							object.wait();
							log.debug(getPercent()+"\t"+getSpeed());
							if(finished()){
								break;
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.run();
	}
	
	/**
	 * �������ؽ���
	 * @return
	 */
	public String getPercent(){
		if(this.downLen==0){
			return decimalFormat.format(0f);
		}
		Float p =  ((float)this.downLen/(float)this.contentLen);
		return decimalFormat.format(p);
	}
	/**
	 * ���������ٶ�
	 * @return
	 */
	public String getSpeed(){
		int cost = ((int) (System.currentTimeMillis() - date.getTime()))/1000;
		int s = (int) (this.downLen/cost)/1024;
		return String.valueOf(s+"(KB)/s");
	}
	/**
	 * �Ƿ��������
	 * @return
	 */
	public boolean finished(){
		return this.finished;
	}
	
	public static void main(String[] args) {
		try {
			MultiThreadDownload multiThreadDownload = new MultiThreadDownload(1024*1024L);
			URL url = new URL("http://zhangmenshiting.baidu.com/data2/music/65517089/307842151200128.mp3?xcode=53102624c6c63d206dbeaf3b8ae12d9080af3c8af038c7a6");
//			URL url = new URL("http://www.baidu.com/img/bdlogo.gif");
			// �첽�����ļ�
			multiThreadDownload.downFile(url, "d:\\multidown\\", "",true);
			// ͬ�������ļ�
//			multiThreadDownload.downLoad(url, "d:\\multidown\\", "");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DownLoadException e) {
			e.printStackTrace();
		} 
	}
	
}	
	
	
	class DownLoadBean {
		private URL url;
		private File distFile;
		private int pos=0;
		private int end = 0;
		private int current = 0;
		
		public DownLoadBean(int pos, int end, int current) {
			super();
			this.pos = pos;
			this.end = end;
			this.current = current;
		}
		
		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}

		public int getLength() {
			return end;
		}

		public void setLength(int length) {
			this.end = length;
		}

		public int getCurrent() {
			return current;
		}

		public void setCurrent(int current) {
			this.current = current;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("DownLoadInfo [url=").append(url).append(", distFile=")
					.append(distFile).append(", pos=").append(pos)
					.append(", length=").append(end).append(", current=")
					.append(current).append("]");
			return builder.toString();
		}
	}
