package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SentenceTest {
    private static Stream<Arguments> equivalentSentences() {
        return Stream.of(
                Arguments.of("This, is a sentence", "this is, a sentence!"),
                Arguments.of("Don't step here.", "dont step here"),
                Arguments.of("whitespace  is\t\tnot\nimportant", "whitespace \nis not important"),
                Arguments.of("CaSe Is NOT ImpORtANT", "Case IS not importanT."),
                Arguments.of("punctuation, separate words", "Punctuation separate &words"));
    }

    @ParameterizedTest
    @MethodSource("equivalentSentences")
    void testParsing(String a, String b) {
        assertThat(Sentence.parse(a))
                .isEqualTo(Sentence.parse(b));
    }
}
