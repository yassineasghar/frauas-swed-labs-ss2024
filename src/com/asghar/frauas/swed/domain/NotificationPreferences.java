package com.asghar.frauas.swed.domain;

/**
 * Represents notification preferences for a user regarding a specific website.
 */
public record NotificationPreferences(int frequency, String communicationChannel, String commEndpoint) {
}
