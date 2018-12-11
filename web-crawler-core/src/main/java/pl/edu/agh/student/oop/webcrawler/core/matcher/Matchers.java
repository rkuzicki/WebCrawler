package pl.edu.agh.student.oop.webcrawler.core.matcher;

/**
 * Utility class for {@link Matcher}s.
 */
public class Matchers {
    private Matchers() {

    }

    /**
     * @param matchers the given matchers
     *
     * @return a matcher that will succeed iff every given matcher succeeds
     */
    public static Matcher matchEvery(Matcher... matchers) {
        return new AndMatcher(matchers);
    }

    /**
     * @param matchers the given matchers
     *
     * @return a matcher that will succeed iff at least one of the given matchers succeeds
     */
    public static Matcher matchAny(Matcher... matchers) {
        return new OrMatcher(matchers);
    }

    /**
     * Returns a negation of the given matcher, i.e. a matcher which will fail if the given one succeeds and vice versa.
     *
     * @param matcher matcher to negate
     *
     * @return negated matcher
     */
    public static Matcher negate(Matcher matcher) {
        return new NotMatcher(matcher);
    }
}
