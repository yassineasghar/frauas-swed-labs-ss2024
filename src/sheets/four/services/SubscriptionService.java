package sheets.four.services;

import sheets.four.domain.NotificationPreferences;
import sheets.four.domain.User;
import sheets.four.domain.Website;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionService {
    private final Map<User, Map<Website, NotificationPreferences>> userSubscriptions = new HashMap<>();

    public void addUser(User user) {
        userSubscriptions.putIfAbsent(user, new HashMap<>());
    }

    public void subscribe(User user, Website website, NotificationPreferences preferences) {
        userSubscriptions.get(user).put(website, preferences);
        user.addWebsite(website);
    }

    public void unsubscribe(User user, Website website) {
        if (userSubscriptions.containsKey(user)) {
            userSubscriptions.get(user).remove(website);
            user.removeWebsite(website);
        }
    }

    public boolean isSubscribed(User user, Website website) {
        return userSubscriptions.containsKey(user) && userSubscriptions.get(user).containsKey(website);
    }

    public void modifyNotificationPreferences(User user, Website website, NotificationPreferences newPreferences) {
        if (isSubscribed(user, website)) {
            userSubscriptions.get(user).put(website, newPreferences);
        } else {
            throw new IllegalArgumentException("The website is not subscribed.");
        }
    }

    public NotificationPreferences getNotificationPreferences(User user, Website website) {
        return userSubscriptions.get(user).get(website);
    }

    public Map<Website, NotificationPreferences> getUserSubscriptions(User user) {
        return userSubscriptions.get(user);
    }

    public Map<User, Map<Website, NotificationPreferences>> getAllSubscriptions() {
        return userSubscriptions;
    }
}
