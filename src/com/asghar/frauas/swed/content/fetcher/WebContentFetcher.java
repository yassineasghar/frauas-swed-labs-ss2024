package com.asghar.frauas.swed.content.fetcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class WebContentFetcher {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String HTTP_VERB = "GET";

    public static String fetchWebsiteContent(String urlString) throws Exception {
        if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
            if (urlString.contains("localhost:")) {
                urlString = "http://" + urlString;
            } else {
                urlString = "https://" + urlString;
            }
        }

        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HTTP_VERB);
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setInstanceFollowRedirects(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to fetch website content. HTTP error code: " + responseCode);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                return content.toString();
            }
        } catch (Exception e) {
            throw new WebsiteNotAvailableException("Failed to fetch website content from " + urlString, e);
        }
    }

    public static class WebsiteNotAvailableException extends Exception {
        public WebsiteNotAvailableException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
