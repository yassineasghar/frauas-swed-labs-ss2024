package com.asghar.frauas.swed.content.checker;

import com.asghar.frauas.swed.content.comparison.ContentComparison;
import com.asghar.frauas.swed.content.fetcher.WebContentFetcher;
import com.asghar.frauas.swed.domain.Website;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ContentChecker {
    private final Map<String, String> lastContentMap = new HashMap<>();
    private final Map<String, Boolean> initializedMap = new HashMap<>();
    private ContentComparison comparisonStrategy;

    public void setComparisonStrategy(ContentComparison strategy) {
        this.comparisonStrategy = strategy;
    }

    public boolean checkForUpdates(Website website) {
        try {
            String currentContent = WebContentFetcher.fetchWebsiteContent(website.url());
            String lastContent = lastContentMap.get(website.url());

            if (!initializedMap.getOrDefault(website.url(), false)) {
                initializeLastContent(website.url(), currentContent);
                initializedMap.put(website.url(), true);
                return false;
            }

            lastContentMap.put(website.url(), currentContent);

            if (lastContent != null && !comparisonStrategy.compareContent(lastContent, currentContent)) {
                return true;
            }
        } catch (MalformedURLException | URISyntaxException e) {
            System.err.println("Invalid URL format for " + website.url() + ": " + e.getMessage());
        } catch (WebContentFetcher.WebsiteNotAvailableException e) {
            System.err.println("Website " + website.url() + " is not reachable: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error checking for updates - " + website.url() + ": " + e.getMessage());
        }
        return false;
    }

    private void initializeLastContent(String urlString, String content) {
        try {
            System.out.println("Initializing last content: " + urlString);
            lastContentMap.put(urlString, content);
        } catch (Exception err) {
            System.err.println("Error initializing last content: " + err.getMessage() + " Check website availability at: " + urlString);
        }
    }
}