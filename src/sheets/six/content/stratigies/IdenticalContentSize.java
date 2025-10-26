package sheets.six.content.stratigies;

/**
 * Compares content based on identical size.
 */
public class IdenticalContentSize implements ContentComparison {

    @Override
    public boolean compareContent(String oldContent, String newContent) {
        return oldContent.length() == newContent.length();
    }
}
