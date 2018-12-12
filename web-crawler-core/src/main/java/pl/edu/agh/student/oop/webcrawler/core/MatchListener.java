package pl.edu.agh.student.oop.webcrawler.core;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.net.URI;


/**
 * Interface used for determining a behaviour of application when the match occured.
 */
public interface MatchListener {
    void handleMatch(Sentence s, URI u);
}
