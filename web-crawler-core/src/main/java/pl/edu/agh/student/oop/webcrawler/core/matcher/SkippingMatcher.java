package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

public class SkippingMatcher implements Matcher {
    private final Matcher next;
    private final int maxSkip;

    SkippingMatcher(int maxSkip, Matcher next) {
        this.next = next;
        this.maxSkip = maxSkip;
    }

    @Override
    public boolean match(Text text) {
        for (int skip = 0; skip <= maxSkip; ++skip) {
            if (next.match(text.subtext(skip)))
                return true;
        }

        return false;
    }
}
