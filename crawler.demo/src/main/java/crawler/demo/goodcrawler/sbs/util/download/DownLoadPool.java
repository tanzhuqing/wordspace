package crawler.demo.goodcrawler.sbs.util.download;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownLoadPool {

	public ExecutorService pool;
	private static DownLoadPool instance;
	
	private DownLoadPool(){
		pool = Executors.newFixedThreadPool(10);
	}
	public static DownLoadPool  getInstance() {
		if (instance == null) {
			instance = new DownLoadPool();
		}
		return instance;
	}
	
	public Future<?> submit(Callable<?> call){
		return pool.submit(call);
	}
}
