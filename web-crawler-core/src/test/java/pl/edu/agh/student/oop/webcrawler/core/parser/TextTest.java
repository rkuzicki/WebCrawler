package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TextTest {


    /**
     * Tests if Text created by TextParser is immutable
     */
    @Test
    void testTextImmutabilityTP() {
        TextParser tp = new TextParser();
        tp.parse("a");
        Text immutableText = tp.getText();
        String textStringOne = immutableText.toString();
        tp.parse("b");
        String textStringTwo = immutableText.toString();

        assertThat(textStringTwo).isEqualTo(textStringOne);
    }


    /**
     * Tests if Text created by Text builder directly is immutable
     */
    @Test
    void testTextImmutabilityTB() {
        Text.Builder tb = Text.builder();
        tb.addSentence(Sentence.parse("a"));
        Text immutableText = tb.build();
        String textStringOne = immutableText.toString();
        tb.addSentence(Sentence.parse("b"));
        String textStringTwo = immutableText.toString();

        assertThat(textStringTwo).isEqualTo(textStringOne);
    }
}
