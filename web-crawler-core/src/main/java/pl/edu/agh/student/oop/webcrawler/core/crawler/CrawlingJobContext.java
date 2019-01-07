package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Single Job configuration.
 * Consists of the main crawler {@link Configuration}, the website URL, {@link MatchListener},
 * and the current depth.
 */
class CrawlingJobContext {
    private DomainInfo domainInfo;
    private Configuration configuration;
    private int currentDepth;
    private URI uri;

    public static CrawlingJobContext rootContext(Configuration config, URI uri) {
        return new CrawlingJobContext(new DomainInfo(), config, 0, uri);
    }

    private CrawlingJobContext(DomainInfo domainInfo, Configuration configuration, int depth, URI uri) {
        this.domainInfo = domainInfo;
        this.configuration = configuration;
        this.currentDepth = depth;
        this.uri = uri;
    }

    public Configuration configuration() {
        return configuration;
    }

    public List<Matcher> matchers() {
        return configuration.matchers();
    }

    public int currentDepth() {
        return currentDepth;
    }

    public URI uri() {
        return uri;
    }

    /**
     * @param link
     *
     * @return context for the child Job
     */
    public CrawlingJobContext childContext(URI link) {
        return new CrawlingJobContext(
                domainInfo,
                configuration,
                currentDepth + 1,
                link);
    }

    public CrawlingMode currentCrawlingMode() {
        try {
            Optional<Boolean> betterDepth = domainInfo.minVisitDepth(uri())
                    .map(depth -> depth > currentDepth());

            if (!betterDepth.isPresent()) {
                // not yet visited
                return CrawlingMode.FULL;
            } else if (betterDepth.get()) {
                // better depth
                return CrawlingMode.FOLLOW_LINKS;
            } else {
                return CrawlingMode.NONE;
            }
        } finally {
            domainInfo.recordVisit(uri(), currentDepth());
        }
    }
}
