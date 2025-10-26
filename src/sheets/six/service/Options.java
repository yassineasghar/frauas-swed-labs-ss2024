package sheets.six.service;

import sheets.six.domain.NotificationPreferences;
import sheets.six.domain.Website;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Options {
    private static String username = "www";
    private static final List<Website> websites = new ArrayList<>();
    private static NotificationPreferences preferences;

    public static void promptUserInput(Scanner userInput) {
        username = getUsername(userInput);

        collectWebsites(userInput);

        int checkInterval = getCheckInterval(userInput);
        preferences = getNotificationPreferences(userInput, checkInterval);
    }

    private static String getUsername(Scanner userInput) {
        while (true) {
            System.out.print("Enter Your Username: ");
            String input = userInput.nextLine().trim();

            if (isValidUsername(input)) {
                return input;
            } else {
                System.out.println("Invalid username input. Username should only contain letters, digits, '.', '@', '-', '_'.");
            }
        }
    }

    public static boolean isValidUsername(String username) {
        return username.matches("[\\w.@-]+");
    }

    private static void collectWebsites(Scanner userInput) {
        while (true) {
            System.out.print("Enter a website (or type 'done' to finish): ");
            String websiteInput = userInput.nextLine().trim();

            // Done check
            if (websiteInput.equalsIgnoreCase("done")) {
                break;
            }

            // website format val.
            if (isValidWebsiteInput(websiteInput)) {
                websites.add(new Website(websiteInput));
                System.out.println("Website added. You can enter another or type 'done' to finish.");
            } else {
                System.out.println("Invalid website input: " + websiteInput);
            }
        }
    }

    public static boolean isValidWebsiteInput(String input) {
        return input.contains("localhost:") || input.contains(".");
    }

    private static int getCheckInterval(Scanner userInput) {
        while (true) {
            try {
                System.out.print("Enter check interval in seconds: ");
                return Integer.parseInt(userInput.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for check interval.");
            }
        }
    }

    private static NotificationPreferences getNotificationPreferences(Scanner userInput, int checkInterval) {
        String notificationType;
        String commEndpoint = "";

        while (true) {
            try {
                System.out.println("Choose notification type:");
                System.out.println("[1] email");
                System.out.println("[2] sms");
                System.out.println("[3] discord");
                System.out.println("[4] telegram");
                System.out.println("[5] msteams");
                System.out.print("Enter your choice (1-5): ");
                int choice = Integer.parseInt(userInput.nextLine().trim());

                switch (choice) {
                    case 1:
                        notificationType = "email";
                        commEndpoint = getEmail(userInput);
                        return new NotificationPreferences(checkInterval, notificationType, commEndpoint);
                    case 2:
                        notificationType = "sms";
                        commEndpoint = getPhoneNumber(userInput);
                        return new NotificationPreferences(checkInterval, notificationType, commEndpoint);
                    case 3:
                        notificationType = "discord";
                        commEndpoint = userInput.nextLine().trim();
                        return new NotificationPreferences(checkInterval, notificationType, commEndpoint);
                    case 4:
                        notificationType = "telegram";
                        commEndpoint = userInput.nextLine().trim();
                        return new NotificationPreferences(checkInterval, notificationType, commEndpoint);
                    case 5:
                        notificationType = "msteams";
                        commEndpoint = userInput.nextLine().trim();
                        return new NotificationPreferences(checkInterval, notificationType, commEndpoint);
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid choice number.");
            }
        }
    }

    private static String getEmail(Scanner userInput) {
        while (true) {
            System.out.print("Enter email address for notifications: ");
            String email = userInput.nextLine().trim();

            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Invalid email address format.");
            }
        }
    }

    public static boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private static String getPhoneNumber(Scanner userInput) {
        while (true) {
            System.out.print("Enter phone number for SMS (starting with '+' or '00'): ");
            String phoneNumber = userInput.nextLine().trim();

            if (isValidPhoneNumber(phoneNumber)) {
                return phoneNumber;
            } else {
                System.out.println("Invalid phone number format.");
            }
        }
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[+|00]\\d+");
    }

    public static String getUsername() {
        return username;
    }

    public static List<Website> getWebsites() {
        return websites;
    }

    public static NotificationPreferences getPreferences() {
        return preferences;
    }
}
