package pl.edu.agh.student.oop.webcrawler.core.parser;

import java.util.Objects;

public class Word implements Cloneable{
    private final String original;
    private final String simplified;

    public static Word of(String content) {
        return new Word(content);
    }

    Word(String original) {
        this.original = original;
        this.simplified = simplify(original);
    }

    private String simplify(String original) {
        if (original.contains(" "))
            throw new IllegalArgumentException("Original word contains space: '" + original + "'");

        String simplified = original.toLowerCase();
        simplified = simplified.replaceAll("[^\\p{javaLowerCase}]", "");
        return simplified;
    }

    public String original() {
        return original;
    }

    public String simplified() {
        return simplified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(simplified, word.simplified);
    }

    @Override
    public String toString() {
        return original;
    }

    @Override
    public int hashCode() {
        return Objects.hash(simplified);
    }

    @Override
    protected Word clone() {
        return new Word(original);
    }
}
