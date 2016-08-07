package crawler.demo.goodcrawler.sbs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CharMatcher;

public class StringHelper {
	/**
	 * @param isoString
	 * @return
	 */
	public static String isoToUtf8(String isoString){
		if(StringUtils.isBlank(isoString))
			return "";
		try {
			return new String(isoString.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * @param urlString
	 * @return
	 */
	public static String urlDecodeString(String urlString){
		if(StringUtils.isBlank(urlString))
			return "";
		try {
			return URLDecoder.decode(urlString, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 删除特殊字符、字母、数字、空格
	 * @param s
	 * @return
	 */
	public static String removeAB12Blank(String s){
		return CharMatcher.anyOf("abcdefghijklmnopqrstuvwxyz;&ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.~!@#$%^&*()-+= 》《><?").removeFrom(s);
	}
	/**
	 * 删除数字、字母、特殊字符
	 * @param s
	 * @return
	 */
	public static String removeAB12(String s){
		return CharMatcher.anyOf("abcdefghijklmnopqrstuvwxyz;&ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.~!@#$%^&*()-+=》《><?").removeFrom(s);
	}
}
