package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

class AnyMatcher implements Matcher {
    private final Matcher next;

    AnyMatcher(Matcher next) {
        this.next = next;
    }

    @Override
    public boolean match(Sentence sentence) {
        long maxSkip = sentence.words().count();

        for (int skip = 0; skip <= maxSkip; ++skip) {
            if (next.match(sentence.subsentence(skip)))
                return true;
        }

        return false;
    }
}
