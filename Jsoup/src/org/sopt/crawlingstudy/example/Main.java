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
		}
		for (String url : linkList) {
			try {
				// 1차 URL목록을 모두 순회하면서 1차목록별 2차목록을 선별함
				Document doc2 = Jsoup.connect(url).get();
				Elements elements2 = doc2.getAllElements();
				for (Element element : elements2) {
					Attributes attrs = element.attributes();
					for (Attribute attr : attrs) {
						// 2차목록에선 URL이 "http://bbs.ruliweb.com/community/board/300143"로 시작하는 게시글(루리웹
						// 유머게시판)만 수집하도록
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
				}
			} catch (Exception e) {
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