package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

public class Configuration {
    private final Matcher matcher;
    private final Integer maxDepth;
    private final Boolean areSubdomains;

    Configuration(ConfigurationBuilder builder) {
        this.matcher = builder.matcher()
                .orElseThrow(() -> new IllegalStateException("Matcher is not specified"));
        this.maxDepth = builder.maxDepth().orElseThrow(() -> new IllegalStateException("Depth is not specified"));
        this.areSubdomains = builder.areSubdomains();
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }

    public Matcher matcher() {
        return matcher;
    }

    public Integer maxDepth() {
        return maxDepth;
    }

    public Boolean areSubdomains() {
        return areSubdomains;
    }
}
