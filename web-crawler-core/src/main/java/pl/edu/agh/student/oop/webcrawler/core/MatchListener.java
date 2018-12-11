package pl.edu.agh.student.oop.webcrawler.core;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.net.URI;

public interface MatchListener {
    void handleMatch(Sentence s, URI u);
}
