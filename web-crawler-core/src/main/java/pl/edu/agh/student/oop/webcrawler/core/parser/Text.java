package pl.edu.agh.student.oop.webcrawler.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Text {
    private List<Sentence> sentences = new ArrayList<>();

    Text() {

    }

    Text(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void add(Sentence sentence) {
        sentences.add(sentence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return Objects.equals(sentences, text.sentences);
    }

    @Override
    public String toString() {
        return sentences.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentences);
    }
}
