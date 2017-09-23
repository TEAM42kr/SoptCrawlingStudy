package org.sopt.crawlingstudy.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrientationMain {
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://sopt.org/wp/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		InputStream is = con.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}
}
