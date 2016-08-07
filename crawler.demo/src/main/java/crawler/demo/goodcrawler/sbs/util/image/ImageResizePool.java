package crawler.demo.goodcrawler.sbs.util.image;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageResizePool {
	/**
	 * �̳߳�
	 */
	public ExecutorService pool;
	
	private static ImageResizePool instance;
	
	private ImageResizePool(){
		pool = Executors.newFixedThreadPool(10);
	}
	
	public static ImageResizePool getInstance(){
		if(instance == null){
			instance = new ImageResizePool();
		}
		return instance;
	}
	/**
	 * �ύ�߳�
	 * @return 
	 */
	public Future<?> submit(Callable<?> call){
		return pool.submit(call);
	}
}
