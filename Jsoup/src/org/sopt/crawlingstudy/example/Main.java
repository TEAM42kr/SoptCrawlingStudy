package org.sopt.crawlingstudy.example;

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

		Document doc = Jsoup.connect("http://bbs.ruliweb.com/community/board/300143").get();
		Elements elements = doc.getAllElements();
		for (Element element : elements) {
			Attributes attrs = element.attributes();
			for (Attribute attr : attrs) {
				if (attr.getValue().startsWith("http://")) {
					linkList.add(removeHttpParam(attr.getValue()));
				}
			}
			// System.out.println(element.tagName());
		}
		for (String url : linkList) {
			try {
				Document doc2 = Jsoup.connect(url).get();
				Elements elements2 = doc2.getAllElements();
				for (Element element : elements2) {
					Attributes attrs = element.attributes();
					for (Attribute attr : attrs) {
						// URL�� "http://bbs.ruliweb.com/community/board/300143"�� �����ϴ� �Խñ�(�縮�� ���ӰԽ���)��
						// �����ϵ���
						if (attr.getValue().startsWith("http://bbs.ruliweb.com/community/board/300143/read/")) {
							String subUrl = removeHttpParam(attr.getValue());
							System.out.println(subUrl);
							File htmlFile = new File(
									subUrl.replace("http://bbs.ruliweb.com/community/board/300143/read/", ""));
							if (!htmlFile.exists()) {
								PrintWriter pw = new PrintWriter(htmlFile);
								pw.println(Jsoup.connect(url).get().toString());
								pw.flush();
								pw.close();
							}
						}
					}
					// System.out.println(element.tagName());
				}
			} catch (Exception e) {
				// sSystem.out.println("ERROR URL :: " + url);
			}
		}
	}

	private static String removeHttpParam(String originalUrl) {
		int firstParamIndex = originalUrl.indexOf('?');
		if (firstParamIndex == -1) {
			firstParamIndex = originalUrl.length();
		}
		return originalUrl.substring(0, firstParamIndex);
	}
}