package pl.edu.agh.student.oop.webcrawler.frontend.input;

import org.junit.jupiter.api.Test;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matchers;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InputParserTest {

    @Test
    void testSubConditionParser0() {
        String inputSubCondition = "ala * * * ma kota";

        Matcher expectedMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenSkipUpTo(3)
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        Matcher resultMatcher = new InputParser().parseSubCondition(inputSubCondition);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }

    @Test
    void testSubConditionParser1() {
        String inputSubCondition = "ala ma kota";

        Matcher expectedMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        Matcher resultMatcher = new InputParser().parseSubCondition(inputSubCondition);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }

    @Test
    void testSubConditionParser2() {
        String inputSubCondition = "ala      ma kota";

        Matcher expectedMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        Matcher resultMatcher = new InputParser().parseSubCondition(inputSubCondition);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }

    @Test()
    void testSubConditionParser3() {
        String inputSubCondition = "";

        assertThatThrownBy(() ->  new InputParser().parseSubCondition(inputSubCondition))
                .isInstanceOf(IllegalArgumentException.class);
    }



    @Test
    void testConditionParser0() {
        ConditionsListItem inputItem = new ConditionsListItem(
                "ala * * * ma kota",
                "~(ala ma kota)");

        Matcher posMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenSkipUpTo(3)
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        Matcher negMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        Matcher expectedMatcher = Matchers.matchEvery(posMatcher, Matchers.negate(negMatcher));
        Matcher resultMatcher = new InputParser().parseCondition(inputItem);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }

    @Test
    void testConditionParser1() {
        ConditionsListItem inputItem = new ConditionsListItem(
                "ala * * * ma kota",
                "");

        Matcher expectedMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenSkipUpTo(3)
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        Matcher resultMatcher = new InputParser().parseCondition(inputItem);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }

    @Test
    void testConditionParser2() {
        ConditionsListItem inputItem = new ConditionsListItem(
                "",
                "~(ala ma kota)");

        Matcher expectedMatcher = Matchers.negate(
                Matcher.compiler()
                    .thenMatchAny()
                    .thenMatch(Word.of("ala"))
                    .thenMatch(Word.of("ma"))
                    .thenMatch(Word.of("kota"))
                    .compile());

        Matcher resultMatcher = new InputParser().parseCondition(inputItem);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }

    @Test
    void testCombineMatchers0() {
        Matcher posMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenSkipUpTo(3)
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        Matcher negMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        List<Matcher> matchers = new ArrayList<>();
        matchers.add(posMatcher);
        matchers.add(Matchers.negate(negMatcher));

        Matcher expectedMatcher = Matchers.matchAny(posMatcher, Matchers.negate(negMatcher));
        Matcher resultMatcher = new InputParser().combineMatchers(matchers);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }

    @Test
    void testCombineMatchers1() {
        Matcher posMatcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("ala"))
                .thenSkipUpTo(3)
                .thenMatch(Word.of("ma"))
                .thenMatch(Word.of("kota"))
                .compile();

        List<Matcher> matchers = new ArrayList<>();
        matchers.add(posMatcher);

        Matcher expectedMatcher = Matchers.matchAny(posMatcher);
        Matcher resultMatcher = new InputParser().combineMatchers(matchers);

        assertThat(resultMatcher).isEqualToComparingFieldByFieldRecursively(expectedMatcher);
    }
}
