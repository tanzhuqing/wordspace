package crawler.demo.goodcrawler.sbs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {
	public static long parseFileSize(String _size){
		if (_size.toUpperCase().endsWith("K")){
			long size = Long.parseLong(_size.toUpperCase().replace("K", ""));
			return size * 1024;
		}
		
		if (_size.toUpperCase().endsWith("M")){
			long size = Long.parseLong(_size.toUpperCase().replace("M", ""));
			return size * 1024 * 1024;
		}
		
		if (_size.toUpperCase().endsWith("G")){
			long size = Long.parseLong(_size.toUpperCase().replace("G", ""));
			return size * 1024 * 1024 * 1024;
		}
		
		return Long.parseLong(_size);
	}
	
	public static String replaceChinese2Utf8(String source) {
		String str = source;
		try {
			Pattern pattern = Pattern.compile(RegexList.has_chinese_regexp);

			Matcher matcher = pattern.matcher(source);
			while (matcher.find()) {
				String g = matcher.group();
				str = source.replace(g, URLEncoder.encode(g, "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	

	public static String parseSingleProp(String source, Map<String, String> map) {
		Pattern pattern = Pattern.compile(RegexList.property_single_regexp);

		Matcher matcher = pattern.matcher(source);
		if (matcher.find()) {
			String g = matcher.group();
			String key = g.replace("${", "").replace("}", "");
			String value = map.get(key);

			source = source.replace(g, value);
		}

		return source;
	}

	
	public static Date strToDate(String source, String pattern) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			date = format.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String dateToStr(Date source, String pattern) {
		String result = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		result = format.format(source);
		return result;
	}

	/**
	 * ���ַ���ת��Ϊ����
	 * 
	 * @param source
	 *            ��ת�����ַ���
	 * @return int ��ֵ
	 */
	public static int strToInt(String source) {
		int result = 0;
		try {
			result = Integer.parseInt(source);
		} catch (NumberFormatException e) {
			System.out.println(source + "�޷�ת��Ϊ����");
			result = 0;
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * �ж��Ƿ�������
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * @���� ���ַ�������ĸתΪ��д
	 * @param str
	 *            Ҫת�����ַ���
	 * @return String ��ֵ
	 */
	public static String toUpCaseFirst(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			char[] temp = str.toCharArray();
			temp[0] = str.toUpperCase().toCharArray()[0];
			str = String.valueOf(temp);
		}

		return str;
	}

	public static String toLowCaseFirst(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			char[] temp = str.toCharArray();
			temp[0] = str.toLowerCase().toCharArray()[0];
			str = String.valueOf(temp);
		}

		return str;
	}

	/**
	 * ������Ӣ���ַ�������ĸתΪ��д
	 * 
	 * @param str
	 *            Ҫת�����ַ�������
	 * @return �ַ�����
	 */
	public static String[] toUpCaseFirst(String[] str) {
		if (str == null || str.length == 0) {
			return str;
		} else {
			String[] result = new String[str.length];
			for (int i = 0; i < result.length; ++i) {
				result[i] = StringUtil.toUpCaseFirst(str[i]);
			}

			return result;
		}
	}

	public static String[] toLowCaseFirst(String[] str) {
		if (str == null || str.length == 0) {
			return str;
		} else {
			String[] result = new String[str.length];
			for (int i = 0; i < result.length; ++i) {
				result[i] = StringUtil.toLowCaseFirst(str[i]);
			}

			return result;
		}
	}

	public static String hump2ohter(String param, String aother) {
		char other = aother.toCharArray()[0];
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, other
					+ mc.group().toLowerCase());
			i++;
		}

		if (other == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}

		return builder.toString();
	}

	/**
	 * @���� ���ݸ�����regex������ʽ����֤�������ַ���input�Ƿ����
	 * @param input
	 *            ��Ҫ����֤���ַ���
	 * @param regex
	 *            ������ʽ
	 * @return boolean ��ֵ
	 */
	public static boolean verifyWord(String input, String regex) {
		if (input == null) {
			input = "";
		}

		if (regex == null) {
			regex = "";
		}

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		boolean flag = m.matches();

		return flag;
	}

	/**
	 * @���� ת���ַ���������HTML�����е������ַ�
	 * @param source
	 *            ΪҪת�����ַ���
	 * @return String��ֵ
	 */
	public static String changeHTML(String source) {
		String s0 = source.replace("\t\n", "<br />"); // ת���ַ����еĻس�����
		String s1 = s0.replace("&", "&amp;"); // ת���ַ����е�"&"����
		String s2 = s1.replace(" ", "&nbsp;"); // ת���ַ����еĿո�
		String s3 = s2.replace("<", "&lt;"); // ת���ַ����е�"<"����
		String s4 = s3.replace(">", "&gt;"); // ת���ַ����е�">"����

		return s4;
	}

	/**
	 * ��ĳЩ�ַ�תΪHTML��ǩ��
	 * 
	 * @param source
	 * @return
	 */
	public static String toHTML(String source) {
		String s1 = source.replace("&amp;", "&"); // ת���ַ����е�"&"����
		String s2 = s1.replace("&nbsp;", " "); // ת���ַ����еĿո�
		String s3 = s2.replace("&lt;", "<"); // ת���ַ����е�"<"����
		String s4 = s3.replace("&gt;", ">"); // ת���ַ����е�">"����
		String s5 = s4.replace("<br />", "\t\n"); // ת���ַ����еĻس�����

		return s5;
	}

	/**
	 * @���� ȡ�õ�ǰʱ��,������ʽ
	 * @return
	 */
	public static String getNowTime(String format) {
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}

		String now = new java.text.SimpleDateFormat(format)
				.format(java.util.Calendar.getInstance().getTime());
		return now;
	}

	/**
	 * @���� ȡ�õ�ǰʱ��
	 * @return
	 */
	public static String getNowTime() {
		return getNowTime(null);
	}

	/**
	 * @���� ת���ַ�����
	 * @param str
	 *            ΪҪת�����ַ���
	 * @return String ��ֵ
	 */
	public static String toEncoding(String str, String encoding) {
		if (str == null) {
			str = "";
		}
		try {
			str = new String(str.getBytes("ISO-8859-1"), encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("��֧��ת���������");
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * ʹһ�����������Ԫ�ر�һ�����ָ����������������һ���ַ���
	 * 
	 * @param format
	 * @return
	 */
	public static String cutArrayBySepara(String[] source, String separator) {
		if (source == null || source.length == 0 || separator == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < source.length; ++i) {
			if (i == source.length - 1) {
				result.append(source[i]);
			} else {
				result.append(source[i]).append(separator);
			}
		}

		return result.toString();
	}

	public static boolean isNullOrEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	public static String toString(Object obj) {
		if (obj == null)
			return "null";
		return obj.toString();
	}

	public static String join(Collection<?> s, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		Iterator<?> iter = s.iterator();
		while (iter.hasNext()) {
			buffer.append(iter.next());
			if (iter.hasNext()) {
				buffer.append(delimiter);
			}
		}
		return buffer.toString();
	}

	/**
	 * ���ļ����еĺ���תΪUTF8����Ĵ�,�Ա�����ʱ����ȷ��ʾ�����ļ���.
	 * 
	 * @param s
	 *            ԭ�ļ���
	 * @return ���±������ļ���
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ��utf-8����ĺ���תΪ����
	 * 
	 * @param str
	 * @return
	 */
	public static String uriDecoding(String str) {
		String result = str;
		try {
			result = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ��ȡ�ͻ���IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getExceptionString(Exception e) {
		StringBuilder sb = new StringBuilder(e.toString());
		sb.append(getStack(e.getStackTrace()));
		Throwable t = e.getCause();
		if (t != null) {
			sb.append("\r\n <font color='red'> | cause by ").append(
					e.getCause());
			sb.append(getStack(e.getCause().getStackTrace()));
			sb.append("</font>");
		}
		return sb.toString();
	}

	public static String getStack(StackTraceElement[] stes) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement ste : stes) {
			if (ste != null)
				sb.append("\n").append(ste.toString());
		}

		return sb.toString();
	}
}
