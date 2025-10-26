package sheets.six.content.stratigies;

/**
 * Comparison strategy based on identical plain text content.
 */
public class IdenticalTextContent implements ContentComparison {

    private static final String TAG_REGEX = "<[^>]*>";
    private static final String CSS_REGEX = "(?s)<style.*?>.*?</style>";
    private static final String REPLACEMENT = "";

    @Override
    public boolean compareContent(String oldContent, String newContent) {
        String oldText = removeHtmlAndStyles(oldContent);
        String newText = removeHtmlAndStyles(newContent);

        return oldText.equals(newText);
    }

    public String removeHtmlAndStyles(String content) {
        String noTags = content.replaceAll(TAG_REGEX, REPLACEMENT);
        return noTags.replaceAll(CSS_REGEX, REPLACEMENT);
    }
}
