package sheets.six.content.stratigies;

/**
 * Interface for comparing content between two sources.
 */
public interface ContentComparison {
    boolean compareContent(String oldContent, String newContent);
}
