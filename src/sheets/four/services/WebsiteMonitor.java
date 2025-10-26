package sheets.four.services;

import sheets.four.domain.NotificationPreferences;
import sheets.four.domain.User;
import sheets.four.domain.Website;
import sheets.four.notifications.NotificationService;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WebsiteMonitor {
    private final SubscriptionService subscriptionService;
    private final NotificationService notificationService;
    private final ContentChecker contentChecker = new ContentChecker();

    public WebsiteMonitor(SubscriptionService subscriptionService, NotificationService notificationService) {
        this.subscriptionService = subscriptionService;
        this.notificationService = notificationService;
    }

    public void startMonitoring() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                for (Map.Entry<User, Map<Website, NotificationPreferences>> userEntry : subscriptionService.getAllSubscriptions().entrySet()) {
                    User user = userEntry.getKey();
                    for (Map.Entry<Website, NotificationPreferences> entry : userEntry.getValue().entrySet()) {
                        Website website = entry.getKey();
                        NotificationPreferences preferences = entry.getValue();

                        if (contentChecker.checkForUpdates(website)) {
                            notificationService.sendNotification("Update detected on " + website.url(), preferences);
                        } else {
                            System.out.println("No update " + website.url());
                        }
                    }
                }

            }
        }, 0, 10 * 1000);
    }
}
