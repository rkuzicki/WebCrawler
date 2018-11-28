package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

class TrueMatcher implements Matcher {
    private static final Matcher INSTANCE = new TrueMatcher();

    public static Matcher instance() {
        return INSTANCE;
    }

    private TrueMatcher() {

    }

    @Override
    public boolean match(Sentence sentence) {
        return true;
    }
}
