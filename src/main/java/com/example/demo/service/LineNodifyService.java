package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LineNodifyService {

	private final FavoriteService  faroviteService;

	public void notify(Article a) {

		if (a.getChannel().getCreater() == null) {

			String message = "ドラマ「"+a.getChannel().getDrama().getName()+"」の「放送日について」チャンネルでの投稿："+a.getContent();
			List<SiteUser> siteusers = faroviteService.collectFavoriteUsers(a.getChannel().getDrama().getId());

			for (SiteUser u:siteusers) {

				sendNotification(message,u.getToken());

			}
		}

	}

	public void sendNotification(String message, String token) {
		HttpURLConnection connection = null;
		try {

			URL url = new URL("https://notify-api.line.me/api/notify");

			connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);

			connection.setRequestMethod("POST");

			connection.addRequestProperty("Authorization", "Bearer " + token);

			try (OutputStream outputStream = connection.getOutputStream();
					PrintWriter writer = new PrintWriter(outputStream)) {

				writer.append("message=").append(URLEncoder.encode(message, "UTF-8")).flush();
				try (InputStream is = connection.getInputStream();
						BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
					String res = r.lines().collect(Collectors.joining());
					if (!res.contains("\"message\":\"ok\"")) {
						System.out.println(res);
						System.out.println("なんか失敗したっぽい");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
