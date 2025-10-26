package com.asghar.frauas.swed.service;

import com.asghar.frauas.swed.domain.NotificationPreferences;
import com.asghar.frauas.swed.domain.User;
import com.asghar.frauas.swed.domain.Website;
import com.asghar.frauas.swed.observer.Observer;
import com.asghar.frauas.swed.observer.Subject;

import java.util.*;

/**
 * Service for managing subscriptions of users to websites and subjects.
 */
public class SubscriptionService implements Subject {

    private final Map<User, Map<Website, NotificationPreferences>> userSubscriptions = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    public void addUser(User user) {
        userSubscriptions.putIfAbsent(user, new HashMap<>());
    }

    public void subscribe(User user, Website website, NotificationPreferences preferences) {
        userSubscriptions.computeIfAbsent(user, k -> new HashMap<>()).put(website, preferences);
        user.addWebsite(website);
        notifyObservers(website);
    }

    public void unsubscribe(User user, Website website) {
        if (userSubscriptions.containsKey(user)) {
            userSubscriptions.get(user).remove(website);
            user.removeWebsite(website);
            notifyObservers(website);
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
        if (isSubscribed(user, website)) {
            return userSubscriptions.get(user).get(website);
        } else {
            throw new IllegalArgumentException("The user is not subscribed to the website.");
        }
    }

    public Map<User, Map<Website, NotificationPreferences>> getAllSubscriptions() {
        return Collections.unmodifiableMap(userSubscriptions);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Website website) {
        for (Observer observer : observers) {
            observer.update(website);
        }
    }
}
