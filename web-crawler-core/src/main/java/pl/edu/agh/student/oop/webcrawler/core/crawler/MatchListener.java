package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.net.URI;

/**
 * Interface used to provide a handler for when the match occurs.
 */
public interface MatchListener {
    /**
     * This method is executed when a match occurs.
     *
     * @param sentence matched sentence
     * @param source   a resource which includes the matched sentence
     * @param matcher  a matcher which matched this sentence
     */
    void handleMatch(Sentence sentence, URI source, Matcher matcher);

    static MatchListener empty() {
        return (s, u, m) -> {

        };
    }
}
