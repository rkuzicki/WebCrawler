package pl.edu.agh.student.oop.webcrawler.core.configuration;

import pl.edu.agh.student.oop.webcrawler.core.crawler.MatchListener;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.net.URI;
import java.util.*;

/**
 * The class used to build a {@link Configuration} object.
 */
public class ConfigurationBuilder {
    private List<Matcher> matchers = new ArrayList<>();
    private List<String> domains = new ArrayList<>();
    private List<URI> startingPoints = new ArrayList<>();
    private OptionalInt depth = OptionalInt.empty();
    private Optional<MatchListener> matchListener = Optional.empty();
    private Optional<OnStalledListener> onStalled;
    private boolean subdomainsEnabled;

    ConfigurationBuilder() {

    }

    /**
     * Add a matcher. It will match websites' content while crawling.
     *
     * @param matcher matcher to add
     *
     * @return this builder
     */
    public ConfigurationBuilder addMatcher(Matcher matcher) {
        this.matchers.add(matcher);
        return this;
    }

    public ConfigurationBuilder matchers(Collection<? extends Matcher> matchers) {
        this.matchers.addAll(matchers);
        return this;
    }

    /**
     * Set list of domains to crawl. The crawler will traverse links only if the domain matches with this list (or is
     * a subdomain if enabled by {@link #subdomainsEnabled()}).
     *
     * @param domains list of domains to crawl
     *
     * @return this builder
     */
    public ConfigurationBuilder domains(List<String> domains) {
        this.domains = new ArrayList<>(domains);
        return this;
    }

    /**
     * Set starting point. Starting points are the topmost links which will be followed by the crawler.
     *
     * @param startingPoints list of starting points to set
     *
     * @return this builder
     */
    public ConfigurationBuilder startingPoints(List<URI> startingPoints) {
        this.startingPoints = new ArrayList<>(startingPoints);
        return this;
    }

    public ConfigurationBuilder addStartingPoint(URI uri) {
        this.startingPoints.add(uri);
        return this;
    }

    /**
     * Set the depth of crawling. When depth is set to 0, the crawler will crawl only the top level links.
     *
     * @param depth depth of crawling
     *
     * @return this builder
     */
    public ConfigurationBuilder depth(int depth) {
        this.depth = OptionalInt.of(depth);
        return this;
    }

    /**
     * Set whether to crawl subdomains or not.
     *
     * @param subdomainsEnabled if {@code true} subdomain crawling will be enabled
     *
     * @return this builder
     */
    public ConfigurationBuilder subdomainsEnabled(boolean subdomainsEnabled) {
        this.subdomainsEnabled = subdomainsEnabled;
        return this;
    }

    public ConfigurationBuilder matchListener(MatchListener listener) {
        this.matchListener = Optional.of(listener);
        return this;
    }

    public ConfigurationBuilder whenStalled(OnStalledListener listener) {
        this.onStalled = Optional.of(listener);
        return this;
    }

    List<Matcher> matchers() {
        return this.matchers;
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

    boolean subdomainsEnabled() {
        return this.subdomainsEnabled;
    }

    Optional<MatchListener> matchListener() {
        return matchListener;
    }

    Optional<OnStalledListener> onStalledListener() {
        return onStalled;
    }

    public Configuration build() {
        return new Configuration(this);
    }
}
