package sheets.five.services;

import sheets.five.domain.NotificationPreferences;
import sheets.five.domain.User;
import sheets.five.domain.Website;
import sheets.five.notifications.NotificationService;

public class Executable {
    private static final String apacheTomCat = "http://localhost:8080/a6/HomePage";
    private static final String commChannel = "Email";
    private static final String userName = "tobias";
    private static final String emailAddress = "tobias@frauas.de";
    private static final int frequency = 60;

    public static void main(String[] args) {
        SubscriptionService subscriptionService = new SubscriptionService();
        NotificationService notificationService = new NotificationService(subscriptionService);
        WebsiteMonitor websiteMonitor = new WebsiteMonitor(subscriptionService);

        subscriptionService.attach(notificationService);

        Website preferedWebsite = new Website(apacheTomCat);
        Website website2 = new Website("www.o365.com");
        NotificationPreferences preferences = new NotificationPreferences(frequency, commChannel);

        User user = new User(userName, emailAddress);
        subscriptionService.addUser(user);
        subscriptionService.subscribe(user, preferedWebsite, preferences);
        websiteMonitor.startMonitoring();
    }
}
