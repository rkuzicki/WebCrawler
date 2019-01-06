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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordMatcher that = (WordMatcher) o;
        return Objects.equals(next, that.next) &&
                Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, word);
    }
}
