package sheets.five.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final String email;
    private final List<Website> websites = new ArrayList<>();

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Website> getWebsites() {
        return websites;
    }

    public void addWebsite(Website website) {
        websites.add(website);
    }

    public void removeWebsite(Website website) {
        websites.remove(website);
    }
}
