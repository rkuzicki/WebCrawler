package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTest {
    @Test
    void testHtmlParsing() {
        Document document = Jsoup.parse("<html><body><p>First <i>parse</i></p><p>Parsed HTML into a doc." +
                "</p><div>  <div></body></html>");
        TextParser textParser = new TextParser();
        textParser.parse("First parse. ParsedHTML into a doc");
        Text expectedText = textParser.getTextBuilder().build();
        assertThat(new HtmlParser(document).parse()).isEqualTo(expectedText);
    }

    @Test
    void testCleanString() {
        assertThat(HtmlParser.isClean("     ")).isFalse();
        assertThat(HtmlParser.isClean(("<div></div>"))).isFalse();
        assertThat(HtmlParser.isClean((""))).isFalse();
    }
}
