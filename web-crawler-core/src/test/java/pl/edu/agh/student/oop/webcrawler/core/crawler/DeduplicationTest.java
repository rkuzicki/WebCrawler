package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.matchers.Times;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.StandardMatchers;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class DeduplicationTest extends CrawlerTestBase {
    @BeforeEach
    void setUp() {
        startMockServer();
    }

    @AfterEach
    void tearDown() {
        stopMockServer();
    }

    @Test
    void testCycle() throws URISyntaxException, InterruptedException {
        registerEndpoint("/root", "test <a href='/a'>a</a>");
        registerEndpoint("/a", "test <a href='/b'>b</a>");
        registerEndpoint("/b", "test <a href='/c'>c</a>");
        registerEndpoint("/c", "test <a href='/a'>a</a>");

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .depth(100)
                .addMatcher(StandardMatchers.oneWordAnywhere(Word.of("test")))
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
                    new URI(address() + "/c"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <pre>
     *    /--> a --> b --\
     *   /                \
     * root -----> c ----> d --> e
     * </pre>
     *
     * c has to wait in order to be second
     */
    @Test
    void testShortestPath() throws URISyntaxException, InterruptedException {
        registerEndpoint("/root", "<a href='/a'>a</a> <a href='/c'>c</a>");
        registerEndpoint("/a", "<a href='/b'>b</a>");
        registerEndpoint("/b", "<a href='/d'>d</a>");

        server().when(request().withPath("/c"), Times.exactly(1))
                .respond(response()
                        .withBody(wrapWithBody("test <a href='/d'>d</a>"))
                        .withDelay(TimeUnit.SECONDS, 10));

        registerEndpoint("/d", "<a href='/e'>e</a>");
        registerEndpoint("/e", "finally");

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .threads(2)
                .depth(3)
                .addMatcher(StandardMatchers.oneWordAnywhere(Word.of("finally")))
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
                    new URI(address() + "/e"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
