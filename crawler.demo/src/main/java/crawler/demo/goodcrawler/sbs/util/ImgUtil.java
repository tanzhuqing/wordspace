package crawler.demo.goodcrawler.sbs.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**

 * @desc 图片下载及压缩
 */
public class ImgUtil {

	private static Log log = LogFactory.getLog(ImgUtil.class);
	public static String path = new File("").getAbsolutePath();
	
	
	/**
	 * @param url
	 * @param movieType
	 * @return 下载图片生成缩略图，放置在movieType目录下
	 */
	public static boolean downAndResize(String url,String movieType) {
		String disFile = path + File.separator + movieType + url.substring(url.lastIndexOf('/'));
		File dir = new File(path + File.separator + movieType);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			//下载
			FileUtils.copyURLToFile(new URL(url), new File(disFile),10000,20000);
			 //压缩
			Thumbnails.of(disFile).width(200).outputQuality(0.6f).toFile(disFile);
			
		} catch (Exception e) {
			log.error("###图片下载压缩失败#" + url);
			return false;
		}
		return true;
	}
	
	/**
	 * 下载压缩，Url的md5做文件名，返回文件名
	 * @param url
	 * @param distPath
	 * @return
	 */
	public static String downThenResize(String url,String distPath){
		String fileName = EncryptUtils.encodeMD5(url);
		File path = new File(distPath);
		if(!path.exists()){
			path.mkdirs();
		}
		String suffix = ".jpg";
		if(url.indexOf('.',url.lastIndexOf('/'))>0)
			suffix = url.substring(url.lastIndexOf('.'));
		if(StringUtils.isBlank(suffix)){
			suffix = ".jpg";
		}
		String file = distPath + File.separator + fileName+ suffix;
		File imgFile = new File(file);
		
		try {
			// 下载
			FileUtils.copyURLToFile(new URL(url), imgFile, 10000, 20000);
			// 压缩
			Thumbnails.of(imgFile).width(200)
			.outputQuality(0.6f)
			.toFile(imgFile);
			return imgFile.getName();
		} catch (IOException e) {
			log.error("###图片下载压缩失败#"+url);
			return null;
		}
	}
}
