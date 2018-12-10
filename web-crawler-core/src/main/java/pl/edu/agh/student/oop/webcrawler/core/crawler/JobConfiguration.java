package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

public class JobConfiguration {
    private Matcher matcher;
    private int depth;
    private boolean areSubdomains;

    public JobConfiguration(Matcher matcher, int depth, boolean areSubdomains) {
        this.matcher = matcher;
        this.depth = depth;
        this.areSubdomains = areSubdomains;
    }

    public Matcher matcher() {
        return matcher;
    }

    public int depth() {
        return depth;
    }

    public boolean areSubdomains() {
        return areSubdomains;
    }
}
