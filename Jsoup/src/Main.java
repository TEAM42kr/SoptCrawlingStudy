import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) throws IOException {
		Set<String> linkList = new HashSet<>();

		Document doc = Jsoup.connect("http://bbs.ruliweb.com/community/board/300143/read/35217996").get();
		Elements elements = doc.getAllElements();
		for (Element element : elements) {
			Attributes attrs = element.attributes();
			for (Attribute attr : attrs) {
				if (attr.getValue().startsWith("http://")) {
					int firstParamIndex = attr.getValue().indexOf('?');
					if (firstParamIndex == -1) {
						firstParamIndex = attr.getValue().length();
					} else {
						firstParamIndex = firstParamIndex - 1;
					}
					linkList.add(attr.getValue().substring(0, firstParamIndex));
				}
			}
			// System.out.println(element.tagName());
		}
		int count = 0;
		for (String url : linkList) {
			try {
				Document doc2 = Jsoup.connect(url).get();
				Elements elements2 = doc2.getAllElements();
				for (Element element : elements2) {
					Attributes attrs = element.attributes();
					for (Attribute attr : attrs) {
						if (attr.getValue().startsWith("http://bbs.ruliweb.com/community/board/300143/read/")) {
							System.out.println(attr.getValue());

							PrintWriter pw = new PrintWriter(new File(attr.getValue().replace("http://bbs.ruliweb.com/community/board/300143/read/", "")));
							pw.println(doc2.toString());
							pw.flush();
							pw.close();
							count++;
						}
					}
					// System.out.println(element.tagName());
				}
			} catch (Exception e) {
				System.out.println("ERROR URL :: " + url);
			}
		}
	}
}