package crawler.demo.goodcrawler.sbs.crawler.conf;
/**
 *
 * ������ϵͳȫ�־�̬����
 */
public class GlobalConstants {
	/**
	 * conf�����ļ�·��
	 */
	public static String propertiyFilePaht = "conf.properties";
	/**
	 * ������URL���д�С
	 */
	public static String pendingUrlsQueueSize = "pending.urls.queue.size";
	/**
	 * ������ҳ����д�С
	 */
	public static String pendingPagesQueueSize = "pending.pages.queue.size";
	/**
	 * ʧ��ҳ����д�С
	 */
	public static String failedPagesQueueSize = "failed.pages.queue.size";
	/**
	 * �ȴ��洢����ȡ��Ϣ���д�С
	 */
	public static String pendingStoreMessgeQueueSize = "pending.store.pages.queue.size";
	/**
	 * ������������ȡ��ʧ��ҳ�汸���ļ�·��
	 */
	public static String failedPagesBackupPath = "failed.pages.backup.path";
	/**
	 * �Ƿ���Դ���Ļ��ߴ���ʧ�ܵ�ҳ��
	 */
	public static String ignoreFailedPages = "ignore.failed.pages";
}
