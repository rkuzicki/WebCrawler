package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Text;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.util.Objects;

class WordMatcher implements Matcher {
    private final Matcher next;
    private final Word word;

    WordMatcher(Word word, Matcher next){
        this.next = Objects.requireNonNull(next);
        this.word = word;
    }

    @Override
    public boolean match(Text text) {
        return text.words()
                .findFirst()
                .map(w -> w.equals(word))
                .map(firstMatch -> firstMatch && next.match(text.subtext(1)))
                .orElse(false);
    }
}
