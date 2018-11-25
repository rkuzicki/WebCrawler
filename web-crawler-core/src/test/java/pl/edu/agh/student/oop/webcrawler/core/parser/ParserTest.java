package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {
    @Test
    void testHtmlParsing() {
        Document document = Jsoup.parse("<html><body><p>First <i>parse</i></p><p>Parsed HTML into a doc." +
                "</p><div>  <div></body></html>");
        List<Sentence> sentenceList = new ArrayList<>();
        sentenceList.add(Sentence.parse("First parse"));
        sentenceList.add(Sentence.parse("Parsed HTML into a doc"));
        Text expectedText = new Text(sentenceList);
        assertThat(new HtmlParser(document).parse()).isEqualTo(expectedText);
    }

    @Test
    void testCleanString() {
        assertThat(HtmlParser.isClean("     ")).isEqualTo(false);
        assertThat(HtmlParser.isClean(("<div></div>"))).isEqualTo(false);
        assertThat(HtmlParser.isClean((""))).isEqualTo(false);
    }
}
