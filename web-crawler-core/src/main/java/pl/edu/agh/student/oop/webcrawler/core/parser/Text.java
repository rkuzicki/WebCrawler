package pl.edu.agh.student.oop.webcrawler.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Text {
    private final List<Sentence> sentences;


    private Text(Builder builder) {
        this.sentences = new ArrayList<>(builder.sentences);
    }

    public Stream<Sentence> sentences() {
        return sentences.stream();
    }

    public List<Sentence> getSentences() {
        return sentences;
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


    public static class Builder {
        private List<Sentence> sentences = new ArrayList<>();

        public Builder() { }

        public void addSentence(Sentence sentence) {
            this.sentences.add(sentence);
        }

        public Text build() {
            return new Text(this );
        }

    }
}
