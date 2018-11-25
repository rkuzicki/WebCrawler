package pl.edu.agh.student.oop.webcrawler.core.configuration.matcher;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Text;

public class AndMatcher implements Matcher {
    private final Matcher[] matchers;

    AndMatcher(Matcher... matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean match(Text text) {
        for (Matcher m : matchers) {
            if (!m.match(text)) return false;
        }

        return true;
    }
}
