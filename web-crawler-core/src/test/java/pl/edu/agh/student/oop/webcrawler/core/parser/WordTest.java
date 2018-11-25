package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WordTest {
    @ParameterizedTest
    @CsvSource({
            "'test,', test",
            "Ala, ala",
            "*aWord*, aword"})
    void testSimplification(String a, String b) {
        assertThat(new Word(a))
                .isEqualTo(new Word(b));
    }

    @Test
    void testSimplificationSpace() {
        assertThatThrownBy(() -> new Word("a word"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testHtmlParsing() {
        Document document = Jsoup.parse("<html><body><p>First parse</p><p>Parsed HTML into a doc.</p></body></html>");
        List<Sentence> sentenceList = new ArrayList<>();
        sentenceList.add(Sentence.parse("First parse"));
        sentenceList.add(Sentence.parse("Parsed HTML into a doc"));
        Text expectedText = new Text(sentenceList);
        assertThat(new HTMLParser(document).parse()).isEqualTo(expectedText);
    }
}
