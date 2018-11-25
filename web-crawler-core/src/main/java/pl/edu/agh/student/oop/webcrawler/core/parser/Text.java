package pl.edu.agh.student.oop.webcrawler.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Text {
    private List<Word> words;

    Text(List<Word> words){
        this.words = new ArrayList<>(words);
    }

    public Text subtext(int from) {
        return new Text(words.subList(from, words.size()));
    }

    public Stream<Word> words() {
        return words.stream();
    }
}
