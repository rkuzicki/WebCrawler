package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.net.URI;

/**
 * Single Job configuration.
 * Consists of the main crawler {@link Configuration}, the website URL, {@link MatchListener},
 * and the current depth.
 */
class CrawlingJobContext {
    private Configuration configuration;
    private int currentDepth;
    private MatchListener listener;
    private URI uri;

    public static CrawlingJobContext rootContext(Configuration config, MatchListener listener, URI uri){
        return new CrawlingJobContext(config, 0, listener, uri);
    }

    CrawlingJobContext(Configuration configuration, int depth, MatchListener listener, URI uri) {
        this.configuration = configuration;
        this.currentDepth = depth;
        this.listener = listener;
        this.uri = uri;
    }

    public Configuration configuration() {
        return configuration;
    }

    public Matcher matcher() {
        return configuration.getMatcher();
    }

    public int currentDepth() {
        return currentDepth;
    }

    public MatchListener matchListener() {
        return listener;
    }

    public URI uri() {
        return uri;
    }


    /**
     *
     * @param link
     * @return context for the child Job
     */
    public CrawlingJobContext childContext(URI link) {
        return new CrawlingJobContext(
                configuration,
                currentDepth + 1,
                listener, link);
    }
}
