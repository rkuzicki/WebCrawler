package pl.edu.agh.student.oop.webcrawler.core.parser;


import java.util.Arrays;

/**
 *
 * Class responsible for parsing plain Text into List of Sentences Objects
 * stored by Text.Builder
 *
 */
public class TextParser {

    private static final String SENTENCE_SPLIT_REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s";

    private Text.Builder builder = new Text.Builder();


    /**
     * Parses plain Text into Sentence Objects and each Object adds
     * to Text.Builder
     *
     * @param text - plain Text
     */
    public void parse(String text) {

        Arrays.stream(text.split(SENTENCE_SPLIT_REGEX))
                .map(String::trim)
                .map(Sentence::parse)
                .forEach(builder::addSentence);
    }

    public Text getText() {
        return this.builder.build();
    }


}
