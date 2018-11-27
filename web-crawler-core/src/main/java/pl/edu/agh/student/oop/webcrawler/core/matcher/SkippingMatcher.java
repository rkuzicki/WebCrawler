package pl.edu.agh.student.oop.webcrawler.core.matcher;

import com.google.common.base.Preconditions;
import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

class SkippingMatcher implements Matcher {
    private final Matcher next;
    private final int maxSkip;

    SkippingMatcher(int maxSkip, Matcher next) {
        Preconditions.checkArgument(maxSkip >= 0);

        this.next = next;
        this.maxSkip = maxSkip;
    }

    SkippingMatcher(Matcher next) {
        this.next = next;
        this.maxSkip = -1;
    }

    @Override
    public boolean match(Text text) {
        long maxSkip = text.words().count();
        if (this.maxSkip >= 0) {
            maxSkip = Math.min(maxSkip, this.maxSkip);
        }

        for (int skip = 0; skip <= maxSkip; ++skip) {
            if (next.match(text.subtext(skip)))
                return true;
        }

        return false;
    }
}
