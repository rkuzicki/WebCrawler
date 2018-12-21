package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.crawler.Crawler;
import pl.edu.agh.student.oop.webcrawler.core.crawler.CrawlerMonitor;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * This class contains required configuration used by the {@link Crawler}.
 * To instantiate this class, use {@link #builder()}.
 */
public class Configuration {
    private final Matcher matcher;
    private final List<String> domains;
    private final List<URI> startingPoints;
    private final int depth;
    private final boolean subdomains;
    private final CrawlerMonitor monitor;

    Configuration(ConfigurationBuilder builder) {
        this.matcher = builder.matcher()
                .orElseThrow(() -> new IllegalStateException("Matcher is not specified"));
        this.depth = builder.depth()
                .orElseThrow(() -> new IllegalStateException("Depth is not specified"));
        this.domains = builder.domains();
        this.startingPoints = builder.startingPoints();
        this.subdomains = builder.subdomainsEnabled();
        this.monitor = builder.monitor().orElse(CrawlerMonitor.empty());
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public int getDepth() {
        return depth;
    }

    public List<String> getDomains() {
        return Collections.unmodifiableList(domains);
    }

    public List<URI> getStartingPoints() {
        return Collections.unmodifiableList(startingPoints);
    }

    public CrawlerMonitor monitor() {
        return monitor;
    }

    public boolean areSubdomainsEnabled() {
        return subdomains;
    }
}
