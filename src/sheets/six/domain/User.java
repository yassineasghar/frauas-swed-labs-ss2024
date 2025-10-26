package sheets.six.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user with associated websites they are interested in.
 */
public class User {
    private final String username;
    private final List<Website> websites = new ArrayList<>();

    /**
     * Constructs a User with a username and email address.
     *
     * @param username The username of the user.
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the list of websites the user is interested in.
     *
     * @return The list of websites.
     */
    public List<Website> getWebsites() {
        return websites;
    }

    /**
     * Adds a website to the list of websites the user is interested in.
     *
     * @param website The website to add.
     */
    public void addWebsite(Website website) {
        websites.add(website);
    }

    /**
     * Removes a website from the list of websites the user is interested in.
     *
     * @param website The website to remove.
     */
    public void removeWebsite(Website website) {
        websites.remove(website);
    }
}
