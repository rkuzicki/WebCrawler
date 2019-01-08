package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

/**
 * This class contains standard matchers, so that {@link MatcherCompiler} wouldn't have
 * to be used when dealing with them.
 */
public class StandardMatchers {
    private StandardMatchers() {

    }

    public static Matcher oneWordAnywhere(Word word) {
        return Matcher.compiler()
                .thenMatchAny()
                .thenMatch(word)
                .compile();
    }
}
