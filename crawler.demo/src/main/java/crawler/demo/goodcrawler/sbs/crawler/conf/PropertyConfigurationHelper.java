package crawler.demo.goodcrawler.sbs.crawler.conf;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.sparta.xpath.ThisNodeTest;

public class PropertyConfigurationHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1214247515108445954L;
    private Log log = LogFactory.getLog(this.getClass());
    private static PropertyConfigurationHelper instance = null;
    private Properties properties;
    
    private PropertyConfigurationHelper(){
    	properties = new Properties();
    	try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream(GlobalConstants.propertiyFilePaht));
		} catch (IOException e) {
			log.fatal("≈‰÷√Œƒº˛»± ß [conf.properties]");
			e.printStackTrace();
		}
    }
    
    public static PropertyConfigurationHelper getInstance() {
		if (instance == null) {
			instance = new PropertyConfigurationHelper();
		}
		return instance;
	}
  
	
	public String getString(String propertyName,String defaultValue){
		return properties.getProperty(propertyName, defaultValue);
	}
	
	public int getInt(String propertyName,int defaultValue){
		return Integer.parseInt(properties.getProperty(propertyName, String.valueOf(defaultValue)));
	}
	
	public Object getObject(String propertyName){
		return properties.get(propertyName);
	}
	
	public void destroy(){
		instance = null;
		properties.clear();
		properties = null;
	}
}
