package org.sopt.crawlingstudy.example;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OrientationMain {
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
		// URL url = new URL("http://sopt.org/wp");
		// HttpURLConnection con = (HttpURLConnection) url.openConnection();
		//
		// InputStream is = con.getInputStream();
		// BufferedReader br = new BufferedReader(new InputStreamReader(is));
		// String line;
		// while ((line = br.readLine()) != null) {
		// System.out.println(line);
		// }
		Integer a = 2;
		Document doc = Jsoup.connect("http://sopt.org/wp").get();
		System.out.println(doc.toString());
		Elements elements = doc.getAllElements();
//		for (Object element : elements.toArray()) {
//			org.jsoup.nodes.Attributes attrs = ((Element) element).attributes();
//			for (Attribute attr : attrs.asList()) {
//				if (attr.getValue().startsWith("http://")) {
//					System.out.println(attr.getValue());
//				}
//			}
//		}
		// byte[] responseByte = new byte[2048];
		// int readedByteSize = 0;
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// while ((readedByteSize = is.read(responseByte)) != -1) {
		// baos.write(responseByte, 0, readedByteSize);
		// }
		// System.out.println(baos.toString("UTF-8"));
	}
}
