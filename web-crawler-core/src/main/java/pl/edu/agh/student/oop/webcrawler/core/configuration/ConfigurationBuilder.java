package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ConfigurationBuilder {
    private Matcher matcher = null;
    private OptionalInt depth = OptionalInt.empty();
    private List<String> domains = new ArrayList<>();
    private List<URI> startingPoints = new ArrayList<>();
    private boolean subdomains;

    ConfigurationBuilder() {

    }

    public ConfigurationBuilder matcher(Matcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public ConfigurationBuilder addDomain(String domain) {
        this.domains.add(domain);
        return this;
    }

    public ConfigurationBuilder domains(List<String> domains) {
        this.domains = new ArrayList<>(domains);
        return this;
    }

    public ConfigurationBuilder addStartingPoint(URI startingPoint) {
        startingPoints.add(startingPoint);
        return this;
    }

    public ConfigurationBuilder startingPoints(List<URI> startingPoints) {
        this.startingPoints = new ArrayList<>(startingPoints);
        return this;
    }

    public ConfigurationBuilder depth(int depth) {
        this.depth = OptionalInt.of(depth);
        return this;
    }

    public ConfigurationBuilder subdomains(boolean subdomains) {
        this.subdomains = subdomains;
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

    List<URI> startingPoints() {
        return this.startingPoints;
    }

    public boolean subdomains() {
        return this.subdomains;
    }

    public Configuration build() {
        return new Configuration(this);
    }
}
