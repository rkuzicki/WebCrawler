package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrMatcher orMatcher = (OrMatcher) o;
        return Arrays.equals(matchers, orMatcher.matchers);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matchers);
    }
}
