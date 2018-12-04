package pl.edu.agh.student.oop.webcrawler.core.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sentence {
    private List<Word> words;

    public static Sentence parse(String text) {
        String[] splitWords = text.replaceAll("\\s+", " ").split(" ");
        List<Word> words = Arrays.stream(splitWords)
                .map(Word::new)
                .collect(Collectors.toList());
        return new Sentence(words);
    }

    Sentence(List<Word> words){
        this.words = new ArrayList<>(words);
    }

    public Sentence subsentence(int from) {
        return new Sentence(words.subList(from, words.size()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }

    @Override
    public String toString() {
        return words.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public Stream<Word> words() {
        return words.stream();
    }

}
