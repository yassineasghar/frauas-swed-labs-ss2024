package sheets.five.services;

import sheets.five.domain.Website;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ContentChecker {
    private static final String userAgent = "Mozilla/5.0";
    private static final String httpVerb = "GET";
    private final Map<String, String> lastContentMap = new HashMap<>();
    private boolean initialized = false;

    public boolean checkForUpdates(Website website) {
        try {
            if (!initialized) {
                initializeLastContent(website.url());
                initialized = true;
                return false;
            }

            String currentContent = fetchWebsiteContent(website.url());
            String lastContent = lastContentMap.get(website.url());

            lastContentMap.put(website.url(), currentContent);

            if (!lastContent.equals(currentContent)) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error checking for updates: " + e.getMessage());
        }
        return false;
    }

    private void initializeLastContent(String urlString) {
        try {
            System.out.println("First Request : " + urlString);
            String currentContent = fetchWebsiteContent(urlString);
            lastContentMap.put(urlString, currentContent);
        } catch (Exception e) {
            System.err.println("Error initializing last content: " + e.getMessage());
        }
    }

    private String fetchWebsiteContent(String urlString) throws Exception {
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(httpVerb);

        conn.setRequestProperty("User-Agent", userAgent);
        conn.setInstanceFollowRedirects(true);
        conn.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }
}
