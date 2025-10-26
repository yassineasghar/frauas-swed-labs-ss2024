package sheets.six.notification;

import sheets.six.domain.NotificationPreferences;
import sheets.six.domain.User;
import sheets.six.domain.Website;
import sheets.six.observer.Observer;
import sheets.six.service.SubscriptionService;

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
        // Since the Observer interface expects only the Website object,
        // we need to fetch the relevant user and preferences based on the website.
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
        System.out.println("Notification sent via " + preferences.communicationChannel() + " - "+ preferences.commEndpoint() + ": " + message);
    }
}
