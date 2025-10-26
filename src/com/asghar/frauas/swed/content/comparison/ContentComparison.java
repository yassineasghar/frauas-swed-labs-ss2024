package com.asghar.frauas.swed.content.comparison;

/**
 * Interface for comparing content between two sources.
 */
public interface ContentComparison {
    boolean compareContent(String oldContent, String newContent);
}
