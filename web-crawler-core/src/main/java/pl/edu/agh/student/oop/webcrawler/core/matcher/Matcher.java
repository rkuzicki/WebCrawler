package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

/**
 * Matcher is a simple predicate which takes a {@link Sentence} and decides whether is does or doesn't match
 * internally specified conditions. Usually, implementations of this class will form a type of chain -- after a
 * matcher gives a positive result, subsequent words will be delegated to a next {@link Matcher}, and so on.
 * <p>
 * To create a simple matcher, refer to {@link #compiler()}.
 */
@FunctionalInterface
public interface Matcher {
    /**
     * Create a new {@link MatcherCompiler}, which can be used to create a {@link Matcher}.
     *
     * @return a new {@code MatcherCompiler}
     */
    static MatcherCompiler compiler() {
        return new MatcherCompiler();
    }

    /**
     * This method checks whether the input matches implementation-specific matcher conditions.
     * <p>
     * The generic form of this method allows for a variety of different implementations, including simple
     * conditions, n-ary operators, combining a matcher with an already existing one, etc.
     *
     * @param sentence a sentence to match
     *
     * @return {@code true} if the sentence matches, {@code false} otherwise
     */
    boolean match(Sentence sentence);

    /**
     * Create a matcher which will match the sentence iff both this, and the second matcher, match the sentence.
     *
     * @return a logical conjunction of this and the given matcher
     */
    default Matcher and(Matcher matcher) {
        return Matchers.matchEvery(this, matcher);
    }

    /**
     * Create a matcher which will match the sentence iff either this, or the second matcher (or both), match the
     * sentence.
     *
     * @return a logical disjunction of this and the given matcher
     */
    default Matcher or(Matcher matcher) {
        return Matchers.matchAny(this, matcher);
    }

    /**
     * Create a matcher which will fail to match the sentence if this matcher matches it, and match a sentence if
     * this matcher fails to do so.
     *
     * @return negated matcher
     */
    default Matcher negate() {
        return Matchers.negate(this);
    }
}
