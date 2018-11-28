package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MatcherCompiler {
    private interface MatcherProvider {
        Matcher create(Matcher next);
    }

    private List<MatcherProvider> matchers = new ArrayList<>();

    MatcherCompiler() {

    }

    public MatcherCompiler matchAny() {
        matchers.add(AnyMatcher::new);
        return this;
    }

    public MatcherCompiler thenMatch(Word word) {
        matchers.add(next -> new WordMatcher(word, next));
        return this;
    }

    public MatcherCompiler thenSkip(int maxWords) {
        matchers.add(next -> new SkippingMatcher(maxWords, next));
        return this;
    }

    public Matcher compile() {
        Matcher compiled = EndMatcher.instance();

        ListIterator<MatcherProvider> i = matchers.listIterator(matchers.size());
        while (i.hasPrevious()) {
            MatcherProvider nextProvider = i.previous();
            compiled = nextProvider.create(compiled);
        }

        return compiled;
    }
}
