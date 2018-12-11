package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.util.List;
import java.util.Optional;

public class ConfigurationBuilder {
    private Matcher matcher = null;
    private List<String> domains;
    private List<String> webPages;
    private int depth;
    private boolean subdomains;

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
    public ConfigurationBuilder webPages(List<String> webPages) {
        this.webPages = webPages;
        return this;
    }
    public ConfigurationBuilder depth(int depth) {
        this.depth = depth;
        return this;
    }
    public ConfigurationBuilder subdomains(boolean subdomains) {
        this.subdomains = subdomains;
        return this;
    }

    Optional<Matcher> matcher() {
        return Optional.ofNullable(matcher);
    }

    public List<String> domains() {
        return this.domains;
    }

    public List<String> webPages() {
        return this.webPages;
    }

    public int depth() {
        return  this.depth;
    }

    public boolean subdomains() {
        return this.subdomains;
    }

    public Configuration build(){
        return new Configuration(this);
    }
}
