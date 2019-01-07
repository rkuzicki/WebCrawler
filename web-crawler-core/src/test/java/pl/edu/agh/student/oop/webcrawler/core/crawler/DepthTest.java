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

public class DepthTest extends CrawlerTestBase {
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

    private void setupEndpoints() {
        registerEndpoint("/root", "crawler <a href='/a'>a</a>");
        registerEndpoint("/a", "crawler <a href='/b'>b</a>");
        registerEndpoint("/b", "crawler <a href='/c'>c</a>");
        registerEndpoint("/c", "crawler <a href='/d'>d</a>");
        registerEndpoint("/d", "crawler leaf");
    }

    @Test
    void testDepth1() throws URISyntaxException, InterruptedException {
        setupEndpoints();

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .depth(1)
                .addMatcher(CRAWLER_MATCHER)
                .matchListener((sentence, source, matcher) -> {
                    sources.add(source);
                })
                .addStartingPoint(new URI(address() + "/root"))
                .build();
        Crawler crawler = new Crawler(config);
        crawler.start();

        waitForCrawler();

        try {
            assertThat(sources).containsExactlyInAnyOrder(
                    new URI(address() + "/root"),
                    new URI(address() + "/a"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDepth2() throws URISyntaxException, InterruptedException {
        setupEndpoints();

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .depth(2)
                .addMatcher(CRAWLER_MATCHER)
                .matchListener((sentence, source, matcher) -> {
                    sources.add(source);
                })
                .addStartingPoint(new URI(address() + "/root"))
                .build();
        Crawler crawler = new Crawler(config);
        crawler.start();

        waitForCrawler();

        try {
            assertThat(sources).containsExactlyInAnyOrder(
                    new URI(address() + "/root"),
                    new URI(address() + "/a"),
                    new URI(address() + "/b"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDepth3() throws URISyntaxException, InterruptedException {
        setupEndpoints();

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .depth(4)
                .addMatcher(CRAWLER_MATCHER)
                .matchListener((sentence, source, matcher) -> {
                    sources.add(source);
                })
                .addStartingPoint(new URI(address() + "/root"))
                .build();
        Crawler crawler = new Crawler(config);
        crawler.start();

        waitForCrawler();

        try {
            assertThat(sources).containsExactlyInAnyOrder(
                    new URI(address() + "/root"),
                    new URI(address() + "/a"),
                    new URI(address() + "/b"),
                    new URI(address() + "/c"),
                    new URI(address() + "/d"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDepth4() throws URISyntaxException, InterruptedException {
        setupEndpoints();

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .depth(5)
                .addMatcher(CRAWLER_MATCHER)
                .matchListener((sentence, source, matcher) -> {
                    sources.add(source);
                })
                .addStartingPoint(new URI(address() + "/root"))
                .build();
        Crawler crawler = new Crawler(config);
        crawler.start();

        waitForCrawler();

        try {
            assertThat(sources).containsExactlyInAnyOrder(
                    new URI(address() + "/root"),
                    new URI(address() + "/a"),
                    new URI(address() + "/b"),
                    new URI(address() + "/c"),
                    new URI(address() + "/d"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
