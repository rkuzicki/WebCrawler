package pl.edu.agh.student.oop.webcrawler.core.matcher;

import com.google.common.base.Preconditions;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;

import java.util.Objects;

class SkippingMatcher implements Matcher {
    private final Matcher next;
    private final int maxSkip;

    SkippingMatcher(int maxSkip, Matcher next) {
        Preconditions.checkArgument(maxSkip >= 0);

        this.next = next;
        this.maxSkip = maxSkip;
    }

    @Override
    public boolean match(Sentence sentence) {
        long maxSkip = Math.min(sentence.words().count(), this.maxSkip);

        for (int skip = 0; skip <= maxSkip; ++skip) {
            if (next.match(sentence.subsentence(skip)))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkippingMatcher that = (SkippingMatcher) o;
        return maxSkip == that.maxSkip &&
                Objects.equals(next, that.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, maxSkip);
    }
}
