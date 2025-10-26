package com.asghar.frauas.swed.notification;

import com.asghar.frauas.swed.domain.NotificationPreferences;
import com.asghar.frauas.swed.domain.User;
import com.asghar.frauas.swed.domain.Website;
import com.asghar.frauas.swed.observer.Observer;
import com.asghar.frauas.swed.service.SubscriptionService;

import java.util.Map;

/**
 * Notification service that sends notifications to users about updates on websites.
 */
public class NotificationService implements Observer {

    private final SubscriptionService subscriptionService;

    public NotificationService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void update(Website website) {
        Map<User, Map<Website, NotificationPreferences>> allSubscriptions = subscriptionService.getAllSubscriptions();
        for (Map.Entry<User, Map<Website, NotificationPreferences>> userEntry : allSubscriptions.entrySet()) {
            for (Map.Entry<Website, NotificationPreferences> entry : userEntry.getValue().entrySet()) {
                if (entry.getKey().equals(website)) {
                    NotificationPreferences preferences = entry.getValue();
                    sendNotification("Update detected on " + website.url(), preferences);
                }
            }
        }
    }

    public void sendNotification(String message, NotificationPreferences preferences) {
        System.out.println("Notification sent via " + preferences.communicationChannel() + " - " + preferences.commEndpoint() + " : " + message);
    }
}
