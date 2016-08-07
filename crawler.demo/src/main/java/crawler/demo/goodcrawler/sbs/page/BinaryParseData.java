package crawler.demo.goodcrawler.sbs.page;

public class BinaryParseData implements ParseData {
private static BinaryParseData instance = new BinaryParseData();
public static BinaryParseData getInstance() {
	return instance;
}

public String toString() {
	return "[Binary parse data can not be dumped as string]";
}
}
