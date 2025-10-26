package sheets.four.notifications;


import sheets.four.domain.NotificationPreferences;

public class NotificationService {

    public void sendNotification(String message, NotificationPreferences preferences) {
        // ...
        System.out.println("Notification sent via "
                + preferences.communicationChannel()
                + ": " + message);
    }
}
