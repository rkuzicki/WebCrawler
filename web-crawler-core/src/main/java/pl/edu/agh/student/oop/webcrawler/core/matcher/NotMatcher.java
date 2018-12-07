package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

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
}
