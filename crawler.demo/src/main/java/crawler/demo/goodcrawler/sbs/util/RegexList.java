package crawler.demo.goodcrawler.sbs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexList {
	public static final String property_single_regexp = "\\$\\{[-a-zA-Z0-9_\u4e00-\u9fa5\\.,;:\\|]+\\}";
	public static final String property_regexp = "\\$\\{[-a-zA-Z0-9_\u4e00-\u9fa5]+\\.[-a-zA-Z0-9_\u4e00-\u9fa5]+\\}";
	public static final String path_var_regexp= "\\{[-a-zA-Z0-9_\u4e00-\u9fa5\\.,;:\\|]+\\}";
	public static final String string_regexp = "[-a-zA-Z0-9_\u4e00-\u9fa5\\.,;:\\|]+";
	public static final String has_chinese_regexp = "[\u4e00-\u9fa5]+";
	public static final String all_chinese_regexp = "^[\u4e00-\u9fa5]+$";
	public static final String html_regexp = "<(\\S*?)[^>]*>.*?</\1>|<.*? />";
	public static final String account_regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
	public static final String qq_regexp = "[1-9][0-9]{4,}";
	public static final String ip_regexp = "\\d+\\.\\d+\\.\\d+\\.\\d+";
	public static final String filePath = "^(^\\.|^/|^[a-zA-Z])?:?/[^>^<^\\*^?^:^\\|^\"]+(/$)?";
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		Pattern pattern = Pattern.compile(RegexList.has_chinese_regexp);
		String str ="xxxx΢΢=΢΢=��";
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			String g = matcher.group();
			str = str.replace(g, URLEncoder.encode(g, "utf-8"));
			System.out.println(g);
		}
		System.out.println(str);
		//System.out.println("/users/{name}".matches("\\{[0-9a-zA-Z\\_\u4e00-\u9fa5]+\\}"));
	}
	
	/**
	 * ƥ��ͼ��
	 * 
	 * 
	 * ��ʽ: /���·��/�ļ���.��׺ (��׺Ϊgif,dmp,png)
	 * 
	 * ƥ�� : /forum/head_icon/admini2005111_ff.gif �� admini2005111.dmp
	 * 
	 * 
	 * ��ƥ��: c:/admins4512.gif
	 * 
	 */
	public static final String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";

	/**
	 * ƥ��email��ַ
	 * 
	 * 
	 * ��ʽ: XXX@XXX.XXX.XX
	 * 
	 * ƥ�� : foo@bar.com �� foobar@foobar.com.au
	 * 
	 * 
	 * ��ƥ��: foo@bar �� $$$@bar.com
	 * 
	 */
	public static final String email_regexp = "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";

	/**
	 * ƥ��ƥ�䲢��ȡurl
	 * 
	 * ��ʽ: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
	 * 
	 * ƥ�� : http://www.suncer.com ��news://www
	 * 
	 * ��ȡ(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true
	 * 
	 * ��ƥ��: c:\window
	 * 
	 */
	public static final String url_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * ƥ�䲢��ȡhttp
	 * 
	 * ��ʽ: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX �� ftp://XXX.XXX.XXX ��
	 * https://XXX
	 * 
	 * ƥ�� : http://www.suncer.com:8080/index.html?login=true
	 * 
	 * ��ȡ(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true
	 * 
	 * ��ƥ��: news://www
	 * 
	 */
	public static final String http_regexp = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * ƥ������
	 * 
	 * ��ʽ(��λ��Ϊ0): XXXX-XX-XX �� XXXX XX XX �� XXXX-X-X
	 * 
	 * ��Χ:1900--2099
	 * 
	 * ƥ�� : 2005-04-04
	 * 
	 * ��ƥ��: 01-01-01
	 * 
	 */
	public static final String date_regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
	/**
	 * ƥ��绰
	 * 
	 * ��ʽΪ: 0XXX-XXXXXX(10-13λ��λ����Ϊ0) ��0XXX XXXXXXX(10-13λ��λ����Ϊ0) ��
	 * (0XXX)XXXXXXXX(11-14λ��λ����Ϊ0) �� XXXXXXXX(6-8λ��λ��Ϊ0) ��
	 * XXXXXXXXXXX(11λ��λ��Ϊ0)
	 * 
	 * ƥ�� : 0371-123456 �� (0371)1234567 �� (0371)12345678 �� 010-123456 ��
	 * 010-12345678 �� 12345678912
	 * 
	 * ��ƥ��: 1111-134355 �� 0123456789
	 * 
	 */
	public static final String phone_regexp = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	/**
	 * ƥ�����֤
	 * 
	 * ��ʽΪ: XXXXXXXXXX(10λ) �� XXXXXXXXXXXXX(13λ) �� XXXXXXXXXXXXXXX(15λ) ��
	 * XXXXXXXXXXXXXXXXXX(18λ)
	 * 
	 * ƥ�� : 0123456789123
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ID_card_regexp = "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";

	/**
	 * ƥ���ʱ����
	 * 
	 * ��ʽΪ: XXXXXX(6λ)
	 * 
	 * ƥ�� : 012345
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ZIP_regexp = "^[0-9]{6}$";// ƥ���ʱ����

	/**
	 * �����������ַ���ƥ�� (�ַ����в��������� ��ѧ�η���^ ������' ˫����" �ֺ�; ����, ñ��: ��ѧ����- �Ҽ�����> �������< ��б��\
	 * ���ո�,�Ʊ��,�س����� )
	 * 
	 * ��ʽΪ: x �� һ��һ�ϵ��ַ�
	 * 
	 * 
	 * ƥ�� : 012345
	 * 
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String non_special_char_regexp = "^[^'\"\\;,:-<>\\s].+$";// ƥ���ʱ����

	/**
	 * ƥ��Ǹ������������� + 0)
	 */
	public static final String non_negative_integers_regexp = "^\\d+$";

	/**
	 * ƥ�䲻������ķǸ������������� > 0)
	 */
	public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";

	/**
	 * 
	 * ƥ��������
	 * 
	 */
	public static final String positive_integer_regexp = "^[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ����������������� + 0��
	 * 
	 */
	public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";

	/**
	 * 
	 * ƥ�为����
	 * 
	 */
	public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ������
	 * 
	 */
	public static final String integer_regexp = "^-?\\d+$";

	/**
	 * 
	 * ƥ��Ǹ����������������� + 0��
	 * 
	 */
	public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";

	/**
	 * 
	 * ƥ����������
	 * 
	 */
	public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	/**
	 * 
	 * ƥ��������������������� + 0��
	 * 
	 */
	public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

	/**
	 * 
	 * ƥ�为������
	 * 
	 */
	public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	/**
	 * 
	 * ƥ�両����
	 * 
	 */
	public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��ɵ��ַ���
	 * 
	 */
	public static final String letter_regexp = "^[A-Za-z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ�Ĵ�д��ɵ��ַ���
	 * 
	 */
	public static final String upward_letter_regexp = "^[A-Z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��Сд��ɵ��ַ���
	 * 
	 */
	public static final String lower_letter_regexp = "^[a-z]+$";

	/**
	 * 
	 * ƥ�������ֺ�26��Ӣ����ĸ��ɵ��ַ���
	 * 
	 */
	public static final String letter_number_regexp = "^[A-Za-z0-9]+$";
}
