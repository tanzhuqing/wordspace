package crawler.demo.goodcrawler.sbs.util.image;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

import net.coobird.thumbnailator.Thumbnails;
import crawler.demo.goodcrawler.sbs.crawler.conf.PropertyConfigurationHelper;

public class ImageResize {
private static final String resize_error_log_file = PropertyConfigurationHelper.getInstance().getString("status.save.path.resize.log", "status/reszie-error.log");
	
	/**
	 * Òì²½µÈ±ÈÑ¹ËõÍ¼Ïñ
	 * @param file
	 * @param width
	 * @param quality
	 * @param delOld
	 */
	public void resizeAsynchronous(final File file,final int width,final float quality,final boolean delOld){
		ImageResizePool.getInstance().submit(new Callable<Object>() {
			
			public Boolean call() throws Exception {
				return resize(file, width, quality, delOld);
			}
		});
	}
	/**
	 * Òì²½¹Ì¶¨¸ß¿íÑ¹ËõÍ¼Ïñ
	 * @param file
	 * @param width
	 * @param height
	 * @param delOld
	 */
	public void resizeAsynchronous(final File file,final int width,final int height,final boolean delOld){
		ImageResizePool.getInstance().submit(new Callable<Object>() {
		
			public Boolean call() throws Exception {
				return resize(file, width, height, delOld);
			}
		});
	}
	/**
	 * µÈ±ÈÀýÑ¹ËõÍ¼Ïñ£¬Ñ¹ËõÍ¼Ïñ½«¸²¸Ç¾ÉÍ¼Ïñ
	 * @param file
	 * @param width
	 * @param quality
	 * @param delOld
	 * @return
	 */
	public boolean resize(File file,int width,float quality,boolean delOld){
		try {
			if(delOld){
				Thumbnails.of(file).width(width)
				.outputQuality(quality)
				.toFile(file);
			}else {
				Thumbnails.of(file).width(width)
				.outputQuality(quality)
				.toFile(file.getParent()+"/resize_"+file.getName());
			}
		} catch (IOException e) {
			try {
				FileUtils.write(new File(resize_error_log_file), file.getAbsolutePath() + "\n", true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	/**
	 * °´¹Ì¶¨¸ß¿íÑ¹Ëõ
	 * @param file
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean resize(File file,int width,int height,boolean delOld){
		try {
			if(delOld){
				Thumbnails.of(file).forceSize(width, height)
				.outputQuality(0.6f)
				.toFile(file);
			}else {
				Thumbnails.of(file).forceSize(width, height)
				.outputQuality(0.6f)
				.toFile(file.getParent()+"/resize_"+file.getName());
			}
		} catch (IOException e) {
			try {
				FileUtils.write(new File(resize_error_log_file), file.getAbsolutePath() + "\n", true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
}
