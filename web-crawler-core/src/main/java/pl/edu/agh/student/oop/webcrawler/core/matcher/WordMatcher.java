package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.util.Objects;

class WordMatcher implements Matcher {
    private final Matcher next;
    private final Word word;

    WordMatcher(Word word, Matcher next) {
        this.next = Objects.requireNonNull(next);
        this.word = word;
    }

    @Override
    public boolean match(Sentence sentence) {
        boolean firstMatches = sentence.words()
                .findFirst()
                .map(word::equals)
                .orElse(false);

        return firstMatches && next.match(sentence.subsentence(1));
    }
}
