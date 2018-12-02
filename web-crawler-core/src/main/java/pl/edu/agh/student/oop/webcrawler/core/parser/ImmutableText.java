package pl.edu.agh.student.oop.webcrawler.core.parser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ImmutableText {
    private final List<Sentence> sentences;


    private ImmutableText(TextBuilder builder) {
        this.sentences = builder.sentences
                                .stream()
                                .map(Sentence::clone)
                                .collect(Collectors.toList());
    }

    public Stream<Sentence> sentences() {
        return sentences.stream()
                        .map(Sentence::clone)
                        .collect(Collectors.toList())
                        .stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableText text = (ImmutableText) o;
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


    public static class TextBuilder {
        private List<Sentence> sentences;

        public TextBuilder(List<Sentence> sentences) {
            this.sentences = sentences;
        }


        public ImmutableText build() {
            return new ImmutableText(this );
        }

    }
}
