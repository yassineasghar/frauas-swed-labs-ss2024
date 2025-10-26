package sheets.six;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sheets.six.content.ContentChecker;
import sheets.six.content.stratigies.ContentComparison;
import sheets.six.content.stratigies.IdenticalContentSize;
import sheets.six.content.stratigies.IdenticalHtmlContent;
import sheets.six.content.stratigies.IdenticalTextContent;
import sheets.six.domain.NotificationPreferences;
import sheets.six.domain.User;
import sheets.six.domain.Website;
import sheets.six.service.Options;
import sheets.six.service.SubscriptionService;

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
    @CsvSource({
            "john.doe@hello-123, true",
            "john$doe, false"
    })
    void testUsernameValidation(String username, boolean expected) {
        assertEquals(expected, Options.isValidUsername(username));
    }

    @ParameterizedTest
    @CsvSource({
            "www.hello.com, true",
            "invalid-url, false"
    })
    void testWebsiteURLValidation(String url, boolean expected) {
        assertEquals(expected, Options.isValidWebsiteInput(url));
    }

    @ParameterizedTest
    @CsvSource({
            "user@helloo.com, true",
            "invalid-email, false"
    })
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