package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

class AndMatcher implements Matcher {
    private final Matcher[] matchers;

    AndMatcher(Matcher... matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean match(Sentence sentence) {
        for (Matcher m : matchers) {
            if (!m.match(sentence)) return false;
        }

        return true;
    }
}
