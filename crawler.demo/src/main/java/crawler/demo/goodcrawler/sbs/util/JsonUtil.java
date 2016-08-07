package crawler.demo.goodcrawler.sbs.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class JsonUtil {
	/**
	 * ¹¹Ôìº¯Êý
	 */
	public JsonUtil() {
	}
	
	public static JSONObject generate(List<?> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalProperty", list.size());
        map.put("root", list);
        return JSONObject.fromObject(map);
    }
   
    public static JSONObject javabean2json(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("data", object);
        return JSONObject.fromObject(map);
    }
   
    public static JSONObject objectcollect2json(List<?> list, String total) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalProperty", total);
        map.put("root", list);
        return JSONObject.fromObject(map);
    }
    
	/**
	 * @param args
	 * @desc 
	 */
	public static void main(String[] args) {

	}
}
