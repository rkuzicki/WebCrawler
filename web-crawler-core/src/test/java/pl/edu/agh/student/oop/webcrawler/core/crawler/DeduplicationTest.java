package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
        server().when(request().withPath("/root"))
                .respond(response().withBody(wrapWithBody("test <a href='/a'>a</a>")));
        server().when(request().withPath("/a"))
                .respond(response().withBody(wrapWithBody("test <a href='/b'>b</a>")));
        server().when(request().withPath("/b"))
                .respond(response().withBody(wrapWithBody("test <a href='/c'>c</a>")));
        server().when(request().withPath("/c"))
                .respond(response().withBody(wrapWithBody("test <a href='/a'>a</a>")));

        List<URI> sources = new ArrayList<>();
        Configuration config = basicConfiguration()
                .depth(100)
                .addMatcher(Matcher.compiler().thenMatchAny().thenMatch(Word.of("test")).compile())
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
}
