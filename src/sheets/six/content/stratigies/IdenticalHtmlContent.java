package sheets.six.content.stratigies;

/**
 * Compares HTML content by ignoring tags and styles.
 */
public class IdenticalHtmlContent implements ContentComparison {

    @Override
    public boolean compareContent(String oldContent, String newContent) {
        return oldContent.equals(newContent);
    }
}
