package crawler.demo.goodcrawler.sbs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	private static final String ALGORIGTHM_MD5 = "MD5";
    private static final int CACHE_SIZE = 4096;
    
    public static String createMD5(String input) {
        try {
			return createMD5(input, null);
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
    }
    
    public static String createMD5(String input, String charset) throws Exception {
        byte[] data;
        if (charset != null && !"".equals(charset)) {
            data = input.getBytes(charset);
        } else {
            data = input.getBytes();
        }
        MessageDigest messageDigest = getMD5();
        messageDigest.update(data);
        return byteArrayToHexString(messageDigest.digest());
    }
    
    public static String generateFileMD5(String filePath) throws Exception {
        String md5 = "";
        File file = new File(filePath);
        if (file.exists()) {
            MessageDigest messageDigest = getMD5();
            InputStream in = new FileInputStream(file);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                messageDigest.update(cache, 0, nRead);
            }
            in.close();
            byte data[] = messageDigest.digest();
            md5 = byteArrayToHexString(data);
         }
        return md5;
    }
    
    private static String byteArrayToHexString(byte[] data) {
        // �������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' 
        };
        // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ������Ա�ʾ�� 16 ������Ҫ 32 ���ַ�
        char arr[] = new char[16 * 2];
        int k = 0; // ��ʾת������ж�Ӧ���ַ�λ��
        // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�ת���� 16 �����ַ���ת��
        for (int i = 0; i < 16; i++) {
            byte b = data[i]; // ȡ�� i ���ֽ�
            // ȡ�ֽ��и� 4 λ������ת��, >>>Ϊ�߼����ƣ�������λһ������
            arr[k++] = hexDigits[b >>> 4 & 0xf];
            // ȡ�ֽ��е� 4 λ������ת��
            arr[k++] = hexDigits[b & 0xf];
        }
        // ����Ľ��ת��Ϊ�ַ���
        return new String(arr);
    }
    
    
    private static MessageDigest getMD5() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(ALGORIGTHM_MD5);
    }
}
