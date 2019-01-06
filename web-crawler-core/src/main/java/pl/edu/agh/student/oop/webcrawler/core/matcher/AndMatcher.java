package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndMatcher that = (AndMatcher) o;
        return Arrays.equals(matchers, that.matchers);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matchers);
    }
}
