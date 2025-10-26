package com.asghar.frauas.swed.service;

import com.asghar.frauas.swed.content.checker.ContentChecker;
import com.asghar.frauas.swed.domain.NotificationPreferences;
import com.asghar.frauas.swed.domain.User;
import com.asghar.frauas.swed.domain.Website;
import com.asghar.frauas.swed.notification.NotificationService;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WebsiteMonitor {
    private final SubscriptionService subscriptionService;
    private final ContentChecker contentChecker;

    public WebsiteMonitor(SubscriptionService subscriptionService, ContentChecker contentChecker) {
        this.subscriptionService = subscriptionService;
        this.contentChecker = contentChecker;
        NotificationService notificationService = new NotificationService(subscriptionService);
        this.subscriptionService.registerObserver(notificationService);
    }

    public void startMonitoring() {
        Timer timer = new Timer();

        for (Map.Entry<User, Map<Website, NotificationPreferences>> userEntry : subscriptionService.getAllSubscriptions().entrySet()) {
            for (Map.Entry<Website, NotificationPreferences> entry : userEntry.getValue().entrySet()) {
                Website website = entry.getKey();
                NotificationPreferences preferences = entry.getValue();
                int checkInterval = preferences.frequency();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (contentChecker.checkForUpdates(website)) {
                            subscriptionService.notifyObservers(website);
                        } else {
                            System.out.println("No update on " + website.url());
                        }
                    }
                }, checkInterval * 1000L, checkInterval * 1000L);
            }
        }
    }
}