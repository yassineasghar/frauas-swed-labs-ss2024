package sheets.five.services;

import sheets.five.domain.NotificationPreferences;
import sheets.five.domain.User;
import sheets.five.domain.Website;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WebsiteMonitor {
    private final SubscriptionService subscriptionService;
    private final ContentChecker contentChecker = new ContentChecker();

    public WebsiteMonitor(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
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
                            System.out.println("No update " + website.url());
                        }
                    }
                }
            }
        }, 0, 10 * 1000);
    }
}
