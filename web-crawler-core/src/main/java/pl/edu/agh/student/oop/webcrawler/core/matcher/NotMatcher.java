package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

public class NotMatcher implements Matcher {
    private final Matcher next;

    NotMatcher(Matcher next) {
        this.next = next;
    }

    @Override
    public boolean match(Text text) {
        return !next.match(text);
    }
}
