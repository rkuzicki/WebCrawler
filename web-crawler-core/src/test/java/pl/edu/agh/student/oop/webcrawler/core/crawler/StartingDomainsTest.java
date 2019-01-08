package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.matcher.StandardMatchers;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StartingDomainsTest extends CrawlerTestBase {
    private static final Matcher CRAWLER_MATCHER =
            StandardMatchers.oneWordAnywhere(Word.of("crawler"));

    @BeforeEach
    void setUp() {
        startMockServer();
    }

    @AfterEach
    void tearDown() {
        stopMockServer();
    }

    @Test
    void testMultipleStartingDomains() throws URISyntaxException, InterruptedException {
        registerEndpoint("/root1", "crawler <a href='/a'>a</a>");
        registerEndpoint("/a", "crawler");
        registerEndpoint("/root2", "crawler <a href='/b'>b</a>");
        registerEndpoint("/b", "crawler <a href='/c'>c</a>");
        registerEndpoint("/c", "crawler");
        registerEndpoint("/root3", "crawler");

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .depth(1)
                .addMatcher(CRAWLER_MATCHER)
                .matchListener((sentence, source, matcher) -> sources.add(source))
                .addStartingPoint(new URI(address() + "/root1"))
                .addStartingPoint(new URI(address() + "/root2"))
                .addStartingPoint(new URI(address() + "/root3"))
                .build();
        Crawler crawler = new Crawler(config);
        crawler.start();

        waitForCrawler();

        assertThat(sources).containsExactlyInAnyOrder(
                new URI(address() + "/root1"),
                new URI(address() + "/root2"),
                new URI(address() + "/root3"),
                new URI(address() + "/a"),
                new URI(address() + "/b"));
    }
}
