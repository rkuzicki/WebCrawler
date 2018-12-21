package pl.edu.agh.student.oop.webcrawler.frontend.input;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.matcher.MatcherCompiler;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matchers;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

import java.util.List;
import java.util.stream.Stream;

public class InputConditionsParser {
    private static final String WILD_CARD = "*";
    private static final String SPACE_REGEX = "\\s+";

    /**
     * Parses list of {@link ConditionsListItem}s into a stream of {@link Matcher}s
     * using {@link #parseCondition(ConditionsListItem)} method
     *
     * @param items list of {@link ConditionsListItem} objects
     *
     * @return a stream of {@link Matcher}s
     */
    public Stream<Matcher> parseConditions(List<ConditionsListItem> items) {
        return items.stream().map(this::parseCondition);
    }

    /**
     * Parse one {@link ConditionsListItem} object into {@link Matcher} object
     *
     * @param item - {@link ConditionsListItem} condition to be parsed into Matcher
     *
     * @return new {@link Matcher} object
     */
    Matcher parseCondition(ConditionsListItem item) {
        String posSubCondition = item.getPositiveSubCondition();
        String negSubCondition = cleanNegation(item.getNegativeSubCondition());

        if (posSubCondition.isEmpty()) {
            return Matchers.negate(parseSubCondition(negSubCondition));
        } else if (negSubCondition.isEmpty()) {
            return parseSubCondition(posSubCondition);
        }

        Matcher positiveMatcher = parseSubCondition(posSubCondition);
        Matcher negativeMatcher = parseSubCondition(negSubCondition).negate();

        return positiveMatcher.and(negativeMatcher);
    }

    /**
     * Parses subCondition into {@link Matcher} object.
     * subCondtion is a simple String like "ala ma * kota".
     * This string can contain only letters and "*". If you want to get Matcher
     * that represent negative subCondition you have to use {@link Matchers#negate(Matcher)}
     * method on result of {@link #parseSubCondition(String)} method
     *
     * @param subCondition - String representing positive part of {@link ConditionsListItem}.
     *                     It can contain only letters and "*"
     *
     * @return new {@link Matcher} object
     *
     * @throws IllegalArgumentException if given String is empty
     */
    Matcher parseSubCondition(String subCondition) {
        if (subCondition.isEmpty())
            throw new IllegalArgumentException("Cannot parse an empty string");

        int wildCardCounter = 0;
        MatcherCompiler matcher = Matcher.compiler().thenMatchAny();
        for (String string : subCondition.split(SPACE_REGEX)) {
            if (string.equals(WILD_CARD)) {
                wildCardCounter++;
            } else if (wildCardCounter > 0) {
                matcher.thenSkipUpTo(wildCardCounter);
                matcher.thenMatch(Word.of(string));
                wildCardCounter = 0;
            } else {
                matcher.thenMatch(Word.of(string));
            }
        }

        return matcher.compile();
    }

    private String cleanNegation(String negSubCondition) {
        if (negSubCondition.isEmpty()) {
            return negSubCondition;
        } else {
            return negSubCondition.substring(2, negSubCondition.length() - 1);
        }
    }
}
