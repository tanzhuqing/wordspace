package crawler.demo.goodcrawler.sbs.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * TODO ���ܹ�����
 * MD5��SHA��BASE64
 */
public class EncryptUtils {
	/**
	 * ���ڻ������ַ���
	 */
	private static  String mix = "!@SFDS$$#$^%JVJDfgfd&*ghfhg*)(*_)+fdrte#$5gdwe234#@%SDDFGGFH!@@!";
	
	private static String encode(String str,String method) {
		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			dstr = new BigInteger(1,md.digest()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dstr;
	}
	
	/** 
     * ��MD5�㷨���м��� 
     * @param str ��Ҫ���ܵ��ַ��� 
     * @return MD5���ܺ�Ľ�� 
     */  
	public static String encodeMD5(String str) {
		return encode(str+mix,"MD5");
	}
	
	public static String encodeSHA(String str) {
		return encode(str, "SHA");
	}
	
	public static String encodeBase64(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str.getBytes());
	}
	
	public static String decodeBase64(String str)throws IOException {
		BASE64Decoder encoder = new BASE64Decoder();
		return new String(encoder.decodeBuffer(str));
	}

public static void main(String[] args) throws IOException {
	  String user = "oneadmin";  
      System.out.println("ԭʼ�ַ��� " + user);  
      System.out.println("MD5���� " + encodeMD5(user));  
      System.out.println("SHA���� " + encodeSHA(user));  
      String base64Str = encodeBase64(user);  
      System.out.println("Base64���� " + base64Str);  
      System.out.println("Base64���� " + decodeBase64(base64Str));
}

}

