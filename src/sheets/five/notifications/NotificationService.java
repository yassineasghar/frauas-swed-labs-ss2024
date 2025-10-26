package sheets.five.notifications;

import sheets.five.domain.NotificationPreferences;
import sheets.five.domain.User;
import sheets.five.domain.Website;
import sheets.five.observer.Observer;
import sheets.five.services.SubscriptionService;

import java.util.Map;

public class NotificationService implements Observer {
    private final SubscriptionService subscriptionService;

    public NotificationService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void update(Website website) {
        Map<User, Map<Website, NotificationPreferences>> allSubscriptions = subscriptionService.getAllSubscriptions();
        for (Map.Entry<User, Map<Website, NotificationPreferences>> entry : allSubscriptions.entrySet()) {
            User user = entry.getKey();
            if (subscriptionService.isSubscribed(user, website)) {
                NotificationPreferences preferences = subscriptionService.getNotificationPreferences(user, website);
                sendNotification("Update detected on " + website.url(), preferences);
            }
        }
    }

    public void sendNotification(String message, NotificationPreferences preferences) {
        System.out.println("Notification sent via " + preferences.communicationChannel() + ": " + message);
    }
}
