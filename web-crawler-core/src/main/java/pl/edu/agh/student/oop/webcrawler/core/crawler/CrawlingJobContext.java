package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.net.URI;

public class CrawlingJobContext {
    private Configuration configuration;
    private int currentDepth;
    private MatchListener matchListener;
    private URI url;

    CrawlingJobContext(Configuration configuration, int depth, MatchListener matchListener, URI url) {
        this.configuration = configuration;
        this.currentDepth = depth;
        this.matchListener = matchListener;
        this.url = url;
    }

    public Configuration configuration() {
        return configuration;
    }

    public Matcher matcher() {
        return configuration.matcher();
    }

    public int currentDepth() {
        return currentDepth;
    }

    public MatchListener matchListener() {
        return matchListener;
    }

    public URI url() {
        return url;
    }

    public CrawlingJobContext childContext(URI link) {
        return new CrawlingJobContext(
                configuration,
                currentDepth + 1,
                matchListener, link);
    }
}
