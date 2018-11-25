package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

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
