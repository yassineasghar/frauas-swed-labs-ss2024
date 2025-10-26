package sheets.six;

import sheets.six.content.*;
import sheets.six.content.stratigies.IdenticalTextContent;
import sheets.six.domain.NotificationPreferences;
import sheets.six.domain.User;
import sheets.six.domain.Website;
import sheets.six.service.Options;
import sheets.six.service.SubscriptionService;
import sheets.six.service.WebsiteMonitor;

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

        for (Website website : websites) {
            subscriptionService.subscribe(user, website, preferences);
        }

        ContentChecker contentChecker = new ContentChecker();
        contentChecker.setComparisonStrategy(new IdenticalTextContent());

        WebsiteMonitor websiteMonitor = new WebsiteMonitor(subscriptionService, contentChecker);
        websiteMonitor.startMonitoring();

        scanner.close();
    }
}