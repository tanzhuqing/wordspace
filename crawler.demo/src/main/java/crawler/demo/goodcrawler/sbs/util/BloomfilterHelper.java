package crawler.demo.goodcrawler.sbs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import crawler.demo.goodcrawler.sbs.crawler.conf.PropertyConfigurationHelper;



public class BloomfilterHelper implements Serializable {
	private static final long serialVersionUID = -160403070863080075L;
	private BloomFilter<String> bf = null;
	private static BloomfilterHelper instance = null;
	
	private BloomfilterHelper(){
		init();
	}
	
	private void init() {
	    File file = new File(PropertyConfigurationHelper.getInstance()
	    		.getString("status.save.path","status")
	    		+ File.separator + "filter.good");
	    if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				instance = (BloomfilterHelper)ois.readObject();
				ois.close();
				fis.close();
				System.out.println("recovery bloomfiler...");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	    if (null == bf) {
			bf = new BloomFilter<String>(0.001, 10000);
		}
	}
	
	public boolean exist(String url) {
		if (StringUtils.isBlank(url)) {
			return true;
		}
		return bf.containsOradd(url.getBytes());
	}
	
	public void add(String url) {
		bf.add(url);
	}
	
	
	
}
