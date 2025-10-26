package sheets.six.service;

import sheets.six.content.ContentChecker;
import sheets.six.domain.NotificationPreferences;
import sheets.six.domain.User;
import sheets.six.domain.Website;
import sheets.six.notification.NotificationService;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WebsiteMonitor {
    private final SubscriptionService subscriptionService;
    private final ContentChecker contentChecker;
    private final NotificationService notificationService;

    public WebsiteMonitor(SubscriptionService subscriptionService, ContentChecker contentChecker) {
        this.subscriptionService = subscriptionService;
        this.contentChecker = contentChecker;
        this.notificationService = new NotificationService(subscriptionService);
        this.subscriptionService.registerObserver(this.notificationService);
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
                        if (contentChecker.checkForUpdates(website)) {
                            subscriptionService.notifyObservers(website);
                        } else {
                            // todo : make the first request ignores this output, throw in 2 request !!
                            System.out.println("No update on " + website.url());
                        }
                    }
                }
            }
        }, 0, 15 * 1000);
    }
}
