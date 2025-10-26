package sheets.six.content;

import sheets.six.content.stratigies.ContentComparison;
import sheets.six.domain.Website;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class checks for updates on websites based on content comparison strategies.
 */
public class ContentChecker {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String HTTP_VERB = "GET";
    private final Map<String, String> lastContentMap = new HashMap<>();
    private ContentComparison comparisonStrategy;
    private boolean initialized = false;

    public void setComparisonStrategy(ContentComparison strategy) {
        this.comparisonStrategy = strategy;
    }

    /**
     * Checks for updates on the specified website.
     *
     * @param website The website to check for updates.
     * @return True if an update is detected, false otherwise.
     */
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

            if (!comparisonStrategy.compareContent(lastContent, currentContent)) {
                return true; // Content has changed
            }
        } catch (Exception e) {
            System.err.println("Error checking for updates: " + e.getMessage());
        }
        return false;
    }

    /**
     * Initializes the last fetched content for the specified URL.
     *
     * @param urlString The URL to fetch content from.
     */
    private void initializeLastContent(String urlString) {
        try {
            System.out.println("Initializing last content: " + urlString);
            String currentContent = fetchWebsiteContent(urlString);
            lastContentMap.put(urlString, currentContent);
        } catch (Exception err) {
            System.err.println("Error initializing last content : " + err.getMessage() + " Check website availability at : " + urlString);
        }
    }

    /**
     * Fetches the content of a website from the specified URL.
     *
     * @param urlString The URL of the website.
     * @return The fetched content as a string.
     * @throws Exception If there's an error fetching the content.
     */
    private String fetchWebsiteContent(String urlString) throws Exception {
        if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
            urlString = "http://" + urlString;
        }

        URI uri = new URI(urlString);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(HTTP_VERB);

        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setInstanceFollowRedirects(true);
        conn.connect();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } finally {
            conn.disconnect();
        }
    }
}
