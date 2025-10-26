package com.asghar.frauas.swed.testing;

import com.asghar.frauas.swed.content.checker.ContentChecker;
import com.asghar.frauas.swed.content.comparison.ContentComparison;
import com.asghar.frauas.swed.content.comparison.IdenticalContentSize;
import com.asghar.frauas.swed.content.comparison.IdenticalHtmlContent;
import com.asghar.frauas.swed.content.comparison.IdenticalTextContent;
import com.asghar.frauas.swed.domain.NotificationPreferences;
import com.asghar.frauas.swed.domain.User;
import com.asghar.frauas.swed.domain.Website;
import com.asghar.frauas.swed.service.Options;
import com.asghar.frauas.swed.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TestCases {

    private static final String TEST_USER = "johndoe";
    private static final String TEST_EMAIL = "user@helloo.com";
    private static final String TEST_WEBSITE = "www.helloo.com";

    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        subscriptionService = new SubscriptionService();
        ContentChecker contentChecker = new ContentChecker();
        contentChecker.setComparisonStrategy(new IdenticalTextContent());
    }

    @ParameterizedTest
    @CsvSource({"john.doe@hello-123, true", "john$doe, false"})
    void testUsernameValidation(String username, boolean expected) {
        assertEquals(expected, Options.isValidUsername(username));
    }

    @ParameterizedTest
    @CsvSource({"www.hello.com, true", "invalid-url, false"})
    void testWebsiteURLValidation(String url, boolean expected) {
        assertEquals(expected, Options.isValidWebsiteInput(url));
    }

    @ParameterizedTest
    @CsvSource({"user@helloo.com, true", "invalid-email, false"})
    void testEmailValidation(String email, boolean expected) {
        assertEquals(expected, Options.isValidEmail(email));
    }

    @Test
    void testSubscriptionManagement() {
        User user = new User(TEST_USER);
        Website website = new Website(TEST_WEBSITE);
        NotificationPreferences preferences = new NotificationPreferences(60, "email", TEST_EMAIL);

        subscriptionService.addUser(user);
        subscriptionService.subscribe(user, website, preferences);
        assertTrue(subscriptionService.isSubscribed(user, website), "User should be subscribed");

        subscriptionService.unsubscribe(user, website);
        assertFalse(subscriptionService.isSubscribed(user, website), "User should be unsubscribed");
    }

    @Test
    void testIdenticalContentSize() {
        ContentComparison strategy = new IdenticalContentSize();
        String content1 = "Test $ Hello";
        String content2 = "Same $ Heelo";
        String content3 = "Different length content";

        assertTrue(strategy.compareContent(content1, content2), "Contents with same length should be considered identical");
        assertFalse(strategy.compareContent(content1, content3), "Contents with different length should not be considered identical");
    }

    @Test
    void testIdenticalHtmlContent() {
        ContentComparison strategy = new IdenticalHtmlContent();
        String content1 = "<html><body>Test content</body></html>";
        String content2 = "<html><body>Test content</body></html>";
        String content3 = "<html><body>Different content</body></html>";

        assertTrue(strategy.compareContent(content1, content2), "Identical HTML content should be considered the same");
        assertFalse(strategy.compareContent(content1, content3), "Different HTML content should not be considered the same");
    }

    @Test
    void testIdenticalTextContent() {
        ContentComparison strategy = new IdenticalTextContent();
        String content1 = "<html><body>Test content</body></html>";
        String content2 = "<div>Test content</div>";
        String content3 = "<html><body>Different content</body></html>";

        assertTrue(strategy.compareContent(content1, content2), "Content with same text but different tags should be considered identical");
        assertFalse(strategy.compareContent(content1, content3), "Content with different text should not be considered identical");
    }
}