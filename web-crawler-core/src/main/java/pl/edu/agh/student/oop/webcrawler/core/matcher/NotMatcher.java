package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.util.Objects;

class NotMatcher implements Matcher {
    private final Matcher inner;

    NotMatcher(Matcher inner) {
        this.inner = inner;
    }

    @Override
    public Matcher negate() {
        return inner;
    }

    @Override
    public boolean match(Sentence sentence) {
        return !inner.match(sentence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotMatcher that = (NotMatcher) o;
        return Objects.equals(inner, that.inner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inner);
    }
}
