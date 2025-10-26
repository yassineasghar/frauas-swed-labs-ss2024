package sheets.six.observer;

import sheets.six.domain.Website;

/**
 * Subject interface for managing observers interested in updates on websites.
 */
public interface Subject {

    /**
     * Registers an observer interested in updates on websites.
     *
     * @param observer The observer to register.
     */
    void registerObserver(Observer observer);

    /**
     * Removes an observer no longer interested in updates on websites.
     *
     * @param observer The observer to remove.
     */
    void removeObserver(Observer observer);

    /**
     * Notifies observers about updates on a website.
     *
     * @param website The website that has been updated.
     */
    void notifyObservers(Website website);
}
