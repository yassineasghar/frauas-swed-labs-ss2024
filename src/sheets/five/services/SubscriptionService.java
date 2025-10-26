package sheets.five.services;

import sheets.five.domain.NotificationPreferences;
import sheets.five.domain.User;
import sheets.five.domain.Website;
import sheets.five.observer.Observer;
import sheets.five.observer.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class SubscriptionService implements Subject {
    private final Map<User, Map<Website, NotificationPreferences>> userSubscriptions = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

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

    public Map<User, Map<Website, NotificationPreferences>> getAllSubscriptions() {
        return userSubscriptions;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Website website) {
        for (Observer observer : observers) {
            observer.update(website);
        }
    }
}
