package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ConfigurationBuilder {
    private Matcher matcher = null;
    private OptionalInt depth = OptionalInt.empty();
    private List<String> domains;

    ConfigurationBuilder() {

    }

    public ConfigurationBuilder matcher(Matcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public ConfigurationBuilder domains(List<String> domains) {
        this.domains = domains;
        return this;
    }

    public ConfigurationBuilder depth(int depth) {
        this.depth = OptionalInt.of(depth);
        return this;
    }

    Optional<Matcher> matcher() {
        return Optional.ofNullable(matcher);
    }

    List<String> domains() {
        return this.domains;
    }

    OptionalInt depth() {
        return this.depth;
    }

    public Configuration build() {
        return new Configuration(this);
    }
}