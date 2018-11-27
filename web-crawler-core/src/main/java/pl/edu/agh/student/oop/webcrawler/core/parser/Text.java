package pl.edu.agh.student.oop.webcrawler.core.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Text {
    private List<Word> words;

    public static Text parse(String text) {
        String[] splitWords = text.replaceAll("\\s+", " ").split(" ");
        List<Word> words = Arrays.stream(splitWords)
                .map(Word::new)
                .collect(Collectors.toList());
        return new Text(words);
    }

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
