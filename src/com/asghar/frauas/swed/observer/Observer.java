package com.asghar.frauas.swed.observer;

import com.asghar.frauas.swed.domain.Website;

/**
 * Observer interface for observing updates to a website.
 */
public interface Observer {
    /**
     * Updates the observer about changes to a website.
     *
     * @param website The website that has been updated.
     */
    void update(Website website);
}
