package com.asghar.frauas.swed;

import com.asghar.frauas.swed.content.checker.ContentChecker;
import com.asghar.frauas.swed.content.comparison.IdenticalTextContent;
import com.asghar.frauas.swed.domain.NotificationPreferences;
import com.asghar.frauas.swed.domain.User;
import com.asghar.frauas.swed.domain.Website;
import com.asghar.frauas.swed.service.Options;
import com.asghar.frauas.swed.service.SubscriptionService;
import com.asghar.frauas.swed.service.WebsiteMonitor;

import java.util.List;
import java.util.Scanner;

public class Executable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SubscriptionService subscriptionService = new SubscriptionService();

        Options.promptUserInput(scanner);

        List<Website> websites = Options.getWebsites();
        NotificationPreferences preferences = Options.getPreferences();

        User user = new User(Options.getUsername());
        subscriptionService.addUser(user);

        ContentChecker contentChecker = new ContentChecker();
        contentChecker.setComparisonStrategy(new IdenticalTextContent());

        for (Website website : websites) {
            contentChecker.checkForUpdates(website);
            subscriptionService.subscribe(user, website, preferences);
        }

        WebsiteMonitor websiteMonitor = new WebsiteMonitor(subscriptionService, contentChecker);
        websiteMonitor.startMonitoring();

        scanner.close();
    }
}
