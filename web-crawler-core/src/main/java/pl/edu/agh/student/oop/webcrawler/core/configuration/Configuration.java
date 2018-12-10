package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.util.List;

public class Configuration {
    private final Matcher matcher;
    private final int depth;
    private final List<String> domains;

    Configuration(ConfigurationBuilder builder) {
        this.matcher = builder.matcher()
                .orElseThrow(() -> new IllegalStateException("Matcher is not specified"));
        this.depth = builder.depth();
        this.domains = builder.domains();
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }
}