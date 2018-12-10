package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

public interface MatchListener {
    void handleMatch(Sentence s);
}
