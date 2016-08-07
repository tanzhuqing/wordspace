package crawler.demo.goodcrawler.sbs.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
	/**
	 * ȱʡ��������ʾ��ʽ�� yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * ȱʡ������ʱ����ʾ��ʽ��yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DEFAULT_YEAR_FORMT = "yyyy";

	/**
	 * ˽�й��췽������ֹ�Ը������ʵ����
	 */
	private DateTimeUtil() {
	}

	/**
	 * �õ�ϵͳ��ǰ����ʱ��
	 * 
	 * @return ��ǰ����ʱ��
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ����
	 * 
	 * @return ��ǰ����
	 */
	public static String getDate() {
		return getDateTime(DEFAULT_DATE_FORMAT);
	}

	public static String getDate(Date date) {
		return getDateTime(date, DEFAULT_DATE_FORMAT);
	}
	public static String getDateMin(Date date) {
		return getDateTime(date, "yyyy-MM-dd HH:mm");
	}

	public static String getDate(java.sql.Date date) {
		return getDateTime(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ���ڼ�ʱ��
	 * 
	 * @return ��ǰ���ڼ�ʱ��
	 */
	public static String getDateTime() {
		return getDateTime(DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * �õ�ϵͳ��ǰ���ڼ�ʱ�䣬����ָ���ķ�ʽ��ʽ��
	 * 
	 * @param pattern
	 *            ��ʾ��ʽ
	 * @return ��ǰ���ڼ�ʱ��
	 */
	public static String getDateTime(String pattern) {
		Date datetime = Calendar.getInstance().getTime();

		return getDateTime(datetime, pattern);
	}

	/**
	 * �õ�ϵͳ��ǰ���ڼ�ʱ�䣬����ָ���ķ�ʽ��ʽ��
	 * 
	 * @param pattern
	 *            ��ʾ��ʽ
	 * @return ��ǰ���ڼ�ʱ��
	 */
	public static String getDateTime(String data, String pattern) {
		Date datetime = Calendar.getInstance().getTime();
		// SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

		return getDateTime(datetime, pattern);
	}
	
	public static String getDateTime(Date date) {
        return getDateTime(date, null);
    }
	
	public static String getDateTime(long time) {
	    try {
	        Date date = new Date(time);
	        return getDateTime(date, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
        return null;
    }
	
	/**
	 * �õ���ָ����ʽ��ʽ��������
	 * 
	 * @param date
	 *            ��Ҫ���и�ʽ��������
	 * @param pattern
	 *            ��ʾ��ʽ
	 * @return ����ʱ���ַ���
	 */
	public static String getDateTime(Date date, String pattern) {
		try {
			if (null == pattern || "".equals(pattern)) {
				pattern = DEFAULT_DATETIME_FORMAT;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * �õ���ǰ���
	 * 
	 * @return ��ǰ���
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static int getYearOfDate(Date date){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 return cal.get(Calendar.YEAR);
	}
	/**
	 * �õ���ǰ�·�
	 * 
	 * @return ��ǰ�·�
	 */
	public static int getCurrentMonth() {
		// ��get�õ����·�����ʵ�ʵ�С1����Ҫ����
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * �õ���ǰ��
	 * 
	 * @return ��ǰ��
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	public static java.sql.Timestamp getCurrentSqlDay() {
		return new java.sql.Timestamp(new Date().getTime());
	}

	/**
	 * ȡ�õ�ǰ�����Ժ�����������ڡ����Ҫ�õ���ǰ�����ڣ������ø����� ����Ҫ�õ�������ͬһ������ڣ�������Ϊ-7
	 * 
	 * @param days
	 *            ���ӵ�������
	 * @return �����Ժ������
	 */
	public static Date addDays(int days) {
		return add(getNow(), days, Calendar.DATE);
	}

	/**
	 * ȡ��ָ�������Ժ�����������ڡ����Ҫ�õ���ǰ�����ڣ������ø�����
	 * 
	 * @param date
	 *            ��׼����
	 * @param days
	 *            ���ӵ�������
	 * @return �����Ժ������
	 */
	public static Date addDays(Date date, int days) {
		return add(date, days, Calendar.DATE);
	}

	public static Date addDays(String date, int days) {
		return add(parse(date), days, Calendar.DATE);
	}

	/**
	 * ȡ�õ�ǰ�����Ժ�ĳ�µ����ڡ����Ҫ�õ���ǰ�·ݵ����ڣ������ø�����
	 * 
	 * @param months
	 *            ���ӵ��·���
	 * @return �����Ժ������
	 */
	public static Date addMonths(int months) {
		return add(getNow(), months, Calendar.MONTH);
	}

	/**
	 * ȡ��ָ�������Ժ�ĳ�µ����ڡ����Ҫ�õ���ǰ�·ݵ����ڣ������ø����� ע�⣬���ܲ���ͬһ���ӣ�����2003-1-31����һ������2003-2-28
	 * 
	 * @param date
	 *            ��׼����
	 * @param months
	 *            ���ӵ��·���
	 * @return �����Ժ������
	 */
	public static Date addMonths(Date date, int months) {
		return add(date, months, Calendar.MONTH);
	}
	
	public static Date addMinutes(Date date, int amount) {
        return add(date, amount, Calendar.MINUTE);
    }

	/**
	 * �ڲ�������Ϊָ������������Ӧ������������
	 * 
	 * @param date
	 *            ��׼����
	 * @param amount
	 *            ���ӵ�����
	 * @param field
	 *            ���ӵĵ�λ���꣬�»�����
	 * @return �����Ժ������
	 */
	private static Date add(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	/**
	 * ��������������������� �õ�һ�����ڼ�ȥ�ڶ��������ǰһ������С�ں�һ�����ڣ��򷵻ظ���
	 * 
	 * @param one
	 *            ��һ������������Ϊ��׼
	 * @param two
	 *            �ڶ�������������Ϊ�Ƚ�
	 * @return ���������������
	 */
	public static long diffDays(Date one, Date two) {
		return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * ����������������·��� ���ǰһ������С�ں�һ�����ڣ��򷵻ظ���
	 * 
	 * @param one
	 *            ��һ������������Ϊ��׼
	 * @param two
	 *            �ڶ�������������Ϊ�Ƚ�
	 * @return ������������·���
	 */
	public static int diffMonths(Date one, Date two) {

		Calendar calendar = Calendar.getInstance();

		// �õ���һ�����ڵ���ֺ��·���
		calendar.setTime(one);
		int yearOne = calendar.get(Calendar.YEAR);
		int monthOne = calendar.get(Calendar.MONDAY);

		// �õ��ڶ������ڵ���ݺ��·�
		calendar.setTime(two);
		int yearTwo = calendar.get(Calendar.YEAR);
		int monthTwo = calendar.get(Calendar.MONDAY);

		return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
	}

	/**
	 * ��Objectת������
	 * 
	 * @param date
	 * @return
	 */
	public static Date parse(Object date) {
        return parse((String)date, "");
    }
	
	/**
	 * ���ַ���ת������
	 * @param datestr
	 * @return
	 */
	public static Date parse(String datestr) {
		return parse(datestr, "");
	}
	
	public static Timestamp parseTimestamp(String datestr) {
        Date date = parse(datestr, "");
        date = date == null ? new Date() : date;
        return new Timestamp(date.getTime());
    }

	/**
	 * ��һ���ַ����ø����ĸ�ʽת��Ϊ�������͡�<br>
	 * ע�⣺�������null�����ʾ����ʧ��
	 * 
	 * @param datestr
	 *            ��Ҫ�����������ַ���
	 * @param pattern
	 *            �����ַ����ĸ�ʽ��Ĭ��Ϊ��yyyy-MM-dd������ʽ
	 * @return �����������
	 */
	public static Date parse(String datestr, String pattern) {
		Date date = null;

		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATE_FORMAT;
		}

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(datestr);
		} catch (ParseException e) {
		}

		return date;
	}

	public static java.sql.Timestamp parseSqlDate(String datestr, String pattern) {
		Date date = null;
		
		if (null == pattern || "".equals(pattern)) {
			pattern = DEFAULT_DATE_FORMAT;
		}

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			return new java.sql.Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	/**
	 * ���ر��µ����һ��
	 * 
	 * @return �������һ�������
	 */
	public static Date getMonthLastDay() {
		return getMonthLastDay(getNow());
	}

	/**
	 * ���ظ��������е��·��е����һ��
	 * 
	 * @param date
	 *            ��׼����
	 * @return �������һ�������
	 */
	public static Date getMonthLastDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		// ����������Ϊ��һ�µ�һ��
		calendar.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1, 1);

		// ��ȥ1�죬�õ��ļ����µ����һ��
		calendar.add(Calendar.DATE, -1);

		return calendar.getTime();
	}

	/**
	 * ��ȡ�ϸ��µ����һ��
	 * 
	 * ��ǰ�µ�һ�죬��һ
	 * 
	 * @return
	 */
	public static Date getPreMonthLastDay() {
		// Date date = add(new Date(),-1,Calendar.MONTH);
		// date = getMonthLastDay(date);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.add(Calendar.DATE, -1);

		return calendar.getTime();
	}

	/**
	 * ��ȡ�ϸ��µ�һ������
	 * 
	 * @return
	 */
	public static Date getPreMonthFirstDay() {
		// Date date = add(new Date(),-1,Calendar.MONTH);
		// date = getMonthLastDay(date);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) - 1, 1);

		return calendar.getTime();
	}

	/**
	 * ���ص��µ�һ�������
	 * 
	 * @return
	 */
	public static Date getCurrMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);

		return calendar.getTime();
	}

	/**
	 * ��ȡ���ܵ�һ������� ��һ��Ϊ����һ
	 * 
	 * @return
	 */
	public static Date getPreWeekFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.setFirstDayOfWeek(1);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				day - week - 5);
		return calendar.getTime();
	}

	/**
	 * ��ȡ�������һ������� ���һ��Ϊ������
	 * 
	 * @return
	 */
	public static Date getPreWeekLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.setFirstDayOfWeek(1);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				day + 1 - week);
		return calendar.getTime();
	}

	
	/**
	 * ���������������� 20070830
	 * 
	 * @return
	 */
	public static int getNumberDate() {
		return getNumberDate(new Date());
	}

	public static int getNumberDate(Date date) {
		String str = getDateTime(date, "yyyyMMdd");
		return Integer.parseInt(str);
	}

	public static int getCurrentHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * �������������ڵ�ǰ������һ��֮������ǵ�ǰ����
	 * 
	 * ���ݴ����hourСʱ�����֣��жϵ�ǰ��Сʱ�����֣��Ƿ���ڴ����hour ������ڻ���ڣ��򷵻ص�ǰ��һ������ڣ����С���򷵻�ǰ�������
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date getLastDateByHour(Date date, int hour) {
		Date current = parse(getDate());

		// ��ʾ������֮ǰ
		if (diffDays(parse(getDate(date)), current) >= -1) {
			// ����ǰһ�������
			if (getCurrentHour() >= hour) {
				return addDays(new Date(), -1);
			} else {
				return addDays(new Date(), -2);
			}
		} else {
			return date;
		}
	}

	public static Date convertDate(Timestamp time) {
		return time == null ? null : new Date(time.getTime());
	}

	/**
	 * �õ���ǰʱ���������ʱ����long��ʽ��ʾ �����ʽ��ʾ �õ�����ʽΪ ��ǰʱ��Ϊ������8����ÿ�����8����
	 * 
	 * @return
	 */
	public static long getDayTime() {
		Date date = new Date();
		long l = date.getTime() / (1000 * 3600 * 24);

		return l * 3600 * 24;
	}
	
	public static int[] getDateElement(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    date = date == null ? new Date() : date;
	    calendar.setTime(date);
	    
	    int[] e = new int[6];
	    
	    e[0] = calendar.get(Calendar.YEAR);
	    e[1] = calendar.get(Calendar.MONTH)+1;
	    e[2] = calendar.get(Calendar.DAY_OF_MONTH);
	    
	    e[3] = calendar.get(Calendar.HOUR_OF_DAY);
	    e[4] = calendar.get(Calendar.MINUTE);
	    e[5] = calendar.get(Calendar.SECOND);
	    
	    return e;
	}
	
	public static long getYearMonth() {
	    return getYearMonth(new Date());
	}
	
	public static long getYearMonth(Date date) {	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
	    String str = dateFormat.format(date);	    
	    return Long.parseLong(str);
	}
	
	/**
	 * ��ȡ��Ӧ���ȵ�����
	 * 
	 * 
	 * @param precision
	 * ���� = YEAR ������ 2012-02-15 12:30:10 �򷵻� 2012-01-01 00:00:00
	 * ���� = DATE ������ 2012-02-15 12:30:10 �򷵻� 2012-02-15 00:00:00
	 * @return
	 */
	public static Date getPrecisionDate(int precision) {
		return getPrecisionDate(new Date(), precision);
		
	}
	
	public static Date getPrecisionDate(Date date, int precision) {
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        StringBuilder sb = new StringBuilder();
        if(Calendar.YEAR == precision) {
            sb.append(calendar.get(Calendar.YEAR)).append("-01-01 00:00:00");
        } else if(Calendar.MONTH == precision) {
            sb.append(calendar.get(Calendar.YEAR)).append("-")
              .append(calendar.get(Calendar.MONTH)+1)
              .append("-01 00:00:00");
        } else if(Calendar.DATE == precision) {
            sb.append(calendar.get(Calendar.YEAR)).append("-")
              .append(calendar.get(Calendar.MONTH)+1).append("-")
              .append(calendar.get(Calendar.DATE))
              .append(" 00:00:00");
        } else if(Calendar.HOUR == precision) {
            sb.append(calendar.get(Calendar.YEAR)).append("-")
              .append(calendar.get(Calendar.MONTH)+1).append("-")
              .append(calendar.get(Calendar.DATE)).append(" ")
              .append(calendar.get(Calendar.HOUR))
              .append(":00:00");
        } else if(Calendar.MINUTE == precision) {
            sb.append(calendar.get(Calendar.YEAR)).append("-")
              .append(calendar.get(Calendar.MONTH)+1).append("-")
              .append(calendar.get(Calendar.DATE)).append(" ")
              .append(calendar.get(Calendar.HOUR)).append(":")
              .append(calendar.get(Calendar.MINUTE))
              .append(":00");
        }
        
        return "".equals(sb.toString()) ? calendar.getTime() : parse(sb.toString(), DEFAULT_DATETIME_FORMAT);
	}
	
	// add by zzq
	/**
	 * ����ĳ�������ܵ���һ������
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}
	
	/**
	 * ����ĳ�������ܵ����յ�����
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}
	
	/**
	 * ����ĳ�������ܵ��ܼ�������
	 * @param date
	 * @param day �ܼ�,1��2��3��4��5��6��7
	 * @return
	 */
	public static Date getDayOfWeek(Date date ,int day){
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + day - 1);
		return c.getTime();
	}
	/**
	 * �����ܼ�
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;  
		if(dayOfWeek <0)dayOfWeek=0;
		return dayOfWeek;
	}
}
