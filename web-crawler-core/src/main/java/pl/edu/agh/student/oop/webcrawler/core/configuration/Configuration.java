package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.util.List;

public class Configuration {
    private final Matcher matcher;
    private final List<String> domains;
    private final List<String> webPages;
    private final int depth;
    private final boolean subdomains;


    Configuration(ConfigurationBuilder builder) {
        this.matcher = builder.matcher()
                .orElseThrow(() -> new IllegalStateException("Matcher is not specified"));
        this.domains = builder.domains();
        this.webPages = builder.webPages();
        this.depth = builder.depth();
        this.subdomains = builder.subdomains();

    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }

    public Matcher matcher() {
        return matcher;
    }

    public List<String> domains() {
        return domains;
    }

    public List<String> webPages() {
        return webPages;
    }

    public int depth() {
        return depth;
    }

    public boolean subdomains() {
        return subdomains;
    }
}
