package sheets.four.services;

import sheets.four.domain.NotificationPreferences;
import sheets.four.domain.User;
import sheets.four.domain.Website;
import sheets.four.notifications.NotificationService;

public class Executable {
    private static final String apache = "http://localhost:8080/a6/HomePage";

    public static void main(String[] args) {
        SubscriptionService subscriptionService = new SubscriptionService();
        NotificationService notificationService = new NotificationService();
        WebsiteMonitor websiteMonitor = new WebsiteMonitor(subscriptionService, notificationService);

        Website website1 = new Website(apache);
        NotificationPreferences preferences = new NotificationPreferences(60, "email");

        User user = new User("yassineasghar", "asghar@frauas.edu");
        subscriptionService.addUser(user);
        subscriptionService.subscribe(user, website1, preferences);

        websiteMonitor.startMonitoring();
    }
}
