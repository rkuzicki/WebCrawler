package pl.edu.agh.student.oop.webcrawler.core.matcher;

import org.junit.jupiter.api.Test;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.core.parser.Text;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import static org.assertj.core.api.Assertions.assertThat;

class MatcherCompilerTest {
    @Test
    void testCompiler() {
        Matcher m = Matcher.compiler()
                .matchAny()
                .thenMatch(Word.of("a"))
                .thenMatch(Word.of("b"))
                .compile();

        assertThat(m.match(Sentence.parse("D a, b c"))).isTrue();
    }
}
