package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.net.URI;

/**
 * Interface used to provide a handler for when the match occurs.
 */
public interface MatchListener {
    void handleMatch(Sentence sentence, URI source);

    static MatchListener empty() {
        return (s, u) -> {

        };
    }
}
