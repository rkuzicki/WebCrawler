package pl.edu.agh.student.oop.webcrawler.core.matcher;

import org.junit.jupiter.api.Test;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import static org.assertj.core.api.Assertions.assertThat;

class MatcherTest {
    @Test
    void testMatchAny() {
        Matcher anyMatcher = Matcher.compiler()
                .matchAny()
                .compile();

        assertThat(anyMatcher.match(Sentence.parse("D a, b c"))).isTrue();
        assertThat(anyMatcher.match(Sentence.parse(""))).isTrue();
        assertThat(anyMatcher.match(Sentence.parse("a"))).isTrue();
    }

    @Test
    void testWordMatcher() {
        Matcher matcher = Matcher.compiler()
                .thenMatch(Word.of("test"))
                .compile();

        assertThat(matcher.match(Sentence.parse("test"))).isTrue();
        assertThat(matcher.match(Sentence.parse("a test"))).isFalse();
        assertThat(matcher.match(Sentence.parse("test hello"))).isTrue();
    }

    @Test
    void testSkipMatcher0() {
        Matcher matcher = Matcher.compiler()
                .thenMatch(Word.of("from"))
                .thenSkip(0)
                .thenMatch(Word.of("to"))
                .compile();

        assertThat(matcher.match(Sentence.parse("from to"))).isTrue();
        assertThat(matcher.match(Sentence.parse("from word to"))).isFalse();
        assertThat(matcher.match(Sentence.parse("from word1"))).isFalse();
    }

    @Test
    void testSkipMatcher1() {
        Matcher matcher = Matcher.compiler()
                .thenMatch(Word.of("from"))
                .thenSkip(1)
                .thenMatch(Word.of("to"))
                .compile();

        assertThat(matcher.match(Sentence.parse("from to"))).isTrue();
        assertThat(matcher.match(Sentence.parse("from word to"))).isTrue();
        assertThat(matcher.match(Sentence.parse("from word1 word2 to"))).isFalse();
        assertThat(matcher.match(Sentence.parse("from word1"))).isFalse();
        assertThat(matcher.match(Sentence.parse("from word1 word2"))).isFalse();
    }

    @Test
    void testNegation() {
        Matcher matcher = Matcher.compiler()
                .thenMatch(Word.of("word"))
                .compile().negate();

        assertThat(matcher.match(Sentence.parse("word"))).isFalse();
        assertThat(matcher.match(Sentence.parse("something"))).isTrue();
    }

    @Test
    void testAndMatcher1() {
        Matcher goodMatcher = Matcher.compiler()
                .thenSkip(1)
                .thenMatch(Word.of("dog"))
                .compile();
        Matcher badMatcher = Matcher.compiler()
                .thenMatch(Word.of("bad"))
                .compile().negate();

        Matcher matcher = goodMatcher.and(badMatcher);

        assertThat(matcher.match(Sentence.parse("good dog"))).isTrue();
        assertThat(matcher.match(Sentence.parse("bad dog"))).isFalse();
        assertThat(matcher.match(Sentence.parse("nice dog"))).isTrue();
        assertThat(matcher.match(Sentence.parse("dog"))).isTrue();
        assertThat(matcher.match(Sentence.parse("bad"))).isFalse();
    }

    @Test
    void testAndMatcher2() {
        Matcher goodMatcher = Matcher.compiler()
                .matchAny()
                .thenMatch(Word.of("hello"))
                .compile();
        Matcher badMatcher = Matcher.compiler()
                .matchAny()
                .thenMatch(Word.of("bye"))
                .compile().negate();

        Matcher matcher = goodMatcher.and(badMatcher);

        assertThat(matcher.match(Sentence.parse("hello"))).isTrue();
        assertThat(matcher.match(Sentence.parse("world hello"))).isTrue();
        assertThat(matcher.match(Sentence.parse("hello world"))).isTrue();
        assertThat(matcher.match(Sentence.parse("hello, bye"))).isFalse();
        assertThat(matcher.match(Sentence.parse("bye hello"))).isFalse();
    }
}
