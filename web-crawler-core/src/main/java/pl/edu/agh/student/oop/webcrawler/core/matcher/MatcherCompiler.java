package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * This is a basic builder for {@link Matcher}s. To see the list of possible matchers refer to the corresponding
 * methods of this class.
 * <p>
 * This class allows to reverse the order of matcher declarations. Normally the matchers would need to be specified
 * starting from the last one (i.e. the one matching the end of the input).
 * <p>
 * In order to complete the compilation, call {@link #compile()}, {@link #matchEnd()}} or {@link #compile(Matcher)}.
 * The resulting matcher will consist of the specified matchers and will combine them in such a way that the input
 * will be matched iff every single matcher succeeds. If any matcher fails to match the input, no succeeding matcher
 * will be called.
 * <p>
 * Some of the matchers (such as {@link #thenSkipUpTo(int)}) need to 'fork', i.e. try to match the input in more than one
 * way. It may create undesirably high time complexity, refer to the specific method documentation for details.
 */
public class MatcherCompiler {
    private interface MatcherProvider {
        Matcher create(Matcher next);
    }

    private List<MatcherProvider> matchers = new ArrayList<>();

    MatcherCompiler() {

    }

    /**
     * Add a matcher which will match anything, i.e.:
     * <ul>
     * <li>an empty sentence,</li>
     * <li>one word sentence, or</li>
     * <li>multiple words sentence.</li>
     * </ul>
     * The matching will proceed until the next matcher succeeds or the end of the sentence is reached. In the latter
     * case the matcher will fail to match the input.
     *
     * @return this compiler
     */
    public MatcherCompiler thenMatchAny() {
        matchers.add(AnyMatcher::new);
        return this;
    }

    /**
     * Adds a matcher which will match the given word. If any other input is encountered, the matcher will fail to
     * match the input. The word is matched using {@link Word#equals(Object)}.
     *
     * @param word the word to match
     *
     * @return this compiler
     */
    public MatcherCompiler thenMatch(Word word) {
        matchers.add(next -> new WordMatcher(word, next));
        return this;
    }

    /**
     * Adds a matcher which will match any number of arbitrary words (including zero) up to the given maximum. It
     * will try each possibility until the next matcher succeeds. If the possibilities are exhausted, the matcher fails
     * to match the input.
     *
     * @param maxWords maximum words to skip
     *
     * @return this compiler
     */
    public MatcherCompiler thenSkipUpTo(int maxWords) {
        matchers.add(next -> new SkippingMatcher(maxWords, next));
        return this;
    }

    /**
     * Finalize the compiler by specifying the closing matcher. The matcher will fail to match the sentence if any
     * input is present.
     *
     * @return compiled matcher
     */
    public Matcher matchEnd() {
        return compile(EndMatcher.instance());
    }

    /**
     * Finalize the compiler by specifying the closing matcher. The matcher will always match the input.
     *
     * @return compiled matcher
     */
    public Matcher compile() {
        return compile(TrueMatcher.instance());
    }

    /**
     * Finalize the compiler by specifying a custom closing matcher.
     *
     * @return compiled matcher
     */
    public Matcher compile(Matcher tail) {
        Matcher compiled = tail;

        ListIterator<MatcherProvider> i = matchers.listIterator(matchers.size());
        while (i.hasPrevious()) {
            MatcherProvider nextProvider = i.previous();
            compiled = nextProvider.create(compiled);
        }

        return compiled;
    }
}
