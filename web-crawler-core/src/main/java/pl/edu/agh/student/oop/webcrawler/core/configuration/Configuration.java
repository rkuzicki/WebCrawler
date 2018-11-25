package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

public class Configuration {
    private final Matcher matcher;

    Configuration(ConfigurationBuilder builder) {
        this.matcher = builder.matcher()
                .orElseThrow(() -> new IllegalStateException("Matcher is not specified"));
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }
}
