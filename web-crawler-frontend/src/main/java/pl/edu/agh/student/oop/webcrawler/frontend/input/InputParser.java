package pl.edu.agh.student.oop.webcrawler.frontend.input;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.configuration.ConfigurationBuilder;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.matcher.MatcherCompiler;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matchers;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

import java.util.ArrayList;
import java.util.List;

public class InputParser implements  Parser{

    private static final String WILD_CARD = "*";
    private static final String SPACE_REGEX = "\\s+";

    @Override
    public Configuration createConfiguration(List<ConditionsListItem> items, List<String> domains, String depth) {

        ConfigurationBuilder builder = Configuration.builder();
        builder.matcher(combineMatchers(parseConditions(items)))
               .domains(domains)
               .depth(Integer.parseInt(depth));

        return builder.build();
    }

    @Override
    public Matcher combineMatchers(List<Matcher> matchers) {
        return Matchers.matchAny(matchers.toArray(new Matcher[0]));
    }

    @Override
    public List<Matcher> parseConditions(List<ConditionsListItem> items) {

        List<Matcher> subMatchers = new ArrayList<>();
        for(ConditionsListItem item : items) {
            subMatchers.add(parseCondition(item));
        }

        return subMatchers;
    }

    @Override
    public Matcher parseCondition(ConditionsListItem conditionItem) {
        String posSubCondition = conditionItem.getPositiveSubCondition();
        String negSubCondition = cleanNegation(conditionItem.getNegativeSubCondition());

        Matcher positiveMatcher = parseSubCondition(posSubCondition);
        Matcher negativeMatcher = Matchers.negate(parseSubCondition(negSubCondition));

        return Matchers.matchEvery(positiveMatcher, negativeMatcher);
    }

    @Override
    public Matcher parseSubCondition(String subCondition) {
        int wildCardCounter = 0;
        MatcherCompiler matcher = Matcher.compiler().matchAny();
        for (String string : subCondition.split(SPACE_REGEX)) {
            if (string.equals(WILD_CARD)) wildCardCounter++;
            else if (wildCardCounter > 0) {
                matcher.thenSkip(wildCardCounter);
                matcher.thenMatch(Word.of(string));
                wildCardCounter = 0;
            } else {
                matcher.thenMatch(Word.of(string));
            }
        }

        return matcher.compile();

    }

    private String cleanNegation(String negSubCondition) {
        if(negSubCondition.equals("")) return negSubCondition;
        else return negSubCondition.substring(2, negSubCondition.length()-2);
    }


}
