package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.util.Optional;

public class ConfigurationBuilder {
    private Matcher matcher = null;
    private Integer maxDepth;
    private Boolean areSubdomains = false;

    ConfigurationBuilder() {

    }

    public ConfigurationBuilder matcher(Matcher matcher, int depth, boolean areSubdomains) {
        this.matcher = matcher;
        this.maxDepth = depth;
        this.areSubdomains = areSubdomains;
        return this;
    }

    Optional<Matcher> matcher() {
        return Optional.ofNullable(matcher);
    }

    Optional<Integer> maxDepth() {return Optional.ofNullable(maxDepth);}

    Boolean areSubdomains() {return this.areSubdomains;}

    public Configuration build(){
        return new Configuration(this);
    }
}
