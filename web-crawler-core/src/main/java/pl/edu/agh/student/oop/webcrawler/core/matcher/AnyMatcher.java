package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.util.Objects;

class AnyMatcher implements Matcher {
    private final Matcher next;

    AnyMatcher(Matcher next) {
        this.next = next;
    }

    @Override
    public boolean match(Sentence sentence) {
        long maxSkip = sentence.words().count();

        for (int skip = 0; skip <= maxSkip; ++skip) {
            if (next.match(sentence.subsentence(skip)))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyMatcher that = (AnyMatcher) o;
        return Objects.equals(next, that.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next);
    }
}
