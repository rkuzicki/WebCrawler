package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

class OrMatcher implements Matcher {
    private final Matcher[] matchers;

    OrMatcher(Matcher... matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean match(Sentence sentence) {
        for (Matcher m : matchers) {
            if (m.match(sentence)) return true;
        }

        return false;
    }
}
