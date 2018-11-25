package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

public interface Matcher {
    static MatcherCompiler compiler() {
        return new MatcherCompiler();
    }

    boolean match(Text text);

    default Matcher and(Matcher matcher) {
        return Matchers.matchEvery(this, matcher);
    }

    default Matcher negate() {
        return Matchers.negate(this);
    }
}
