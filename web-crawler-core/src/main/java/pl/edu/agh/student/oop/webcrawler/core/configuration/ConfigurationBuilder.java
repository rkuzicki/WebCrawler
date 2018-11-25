package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.util.Optional;

public class ConfigurationBuilder {
    private Matcher matcher = null;

    ConfigurationBuilder() {

    }

    public ConfigurationBuilder matcher(Matcher matcher) {
        this.matcher = matcher;
        return this;
    }

    Optional<Matcher> matcher() {
        return Optional.ofNullable(matcher);
    }

    public Configuration build(){
        return new Configuration(this);
    }
}
