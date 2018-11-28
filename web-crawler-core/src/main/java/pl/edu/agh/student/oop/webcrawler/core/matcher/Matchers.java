package pl.edu.agh.student.oop.webcrawler.core.matcher;

public class Matchers {
    private Matchers() {

    }

    public static Matcher matchEvery(Matcher... matchers) {
        return new AndMatcher(matchers);
    }

    public static Matcher matchAny(Matcher... matchers) {
        return new OrMatcher(matchers);
    }

    public static Matcher negate(Matcher matcher) {
        return new NotMatcher(matcher);
    }
}
