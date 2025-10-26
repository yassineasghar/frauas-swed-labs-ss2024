package sheets.five.observer;

import sheets.five.domain.Website;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(Website website);
}
