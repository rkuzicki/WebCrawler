package pl.edu.agh.student.oop.webcrawler.frontend.util;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

public class UserInputtedMatcher implements Matcher {
    private final Matcher matcher;
    private final String input;

    public UserInputtedMatcher(Matcher matcher, String input) {
        this.matcher = matcher;
        this.input = input;
    }

    public String getUserInput() {
        return input;
    }

    @Override
    public boolean match(Sentence sentence) {
        return this.matcher.match(sentence);
    }

    @Override
    public Matcher and(Matcher matcher) {
        return this.matcher.and(matcher);
    }

    @Override
    public Matcher or(Matcher matcher) {
        return this.matcher.or(matcher);
    }

    @Override
    public Matcher negate() {
        return this.matcher.negate();
    }

    @Override
    public String toString() {
        return getUserInput();
    }
}
