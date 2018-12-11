package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public class Configuration {
    private final Matcher matcher;
    private final List<String> domains;
    private final List<URI> startingPoints;
    private final int depth;
    private final boolean subdomains;

    Configuration(ConfigurationBuilder builder) {
        this.matcher = builder.matcher()
                .orElseThrow(() -> new IllegalStateException("Matcher is not specified"));
        this.depth = builder.depth()
                .orElseThrow(() -> new IllegalStateException("Depth is not specified"));
        this.domains = builder.domains();
        this.startingPoints = builder.startingPoints();
        this.subdomains = builder.subdomains();
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

    public boolean subdomains() {
        return subdomains;
    }

}
