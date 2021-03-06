package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

class EndMatcher implements Matcher {
    private static final Matcher INSTANCE = new EndMatcher();

    public static Matcher instance() {
        return INSTANCE;
    }

    private EndMatcher() {

    }

    @Override
    public boolean match(Sentence sentence) {
        return sentence.words().count() == 0;
    }
}
