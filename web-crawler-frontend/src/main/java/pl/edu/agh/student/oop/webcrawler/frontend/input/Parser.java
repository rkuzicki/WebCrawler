package pl.edu.agh.student.oop.webcrawler.frontend.input;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matchers;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

import java.util.List;

public interface Parser {

    /**
     * Creates new {@link Configuration} object
     *
     * @param depth - crawling depth - means how many times crawler should go deeper during searching
     * @param domains - list of domains crawler will check
     * @param items - list of {@link ConditionsListItem}
     * @return new Configuration {@link Configuration}
     */
    Configuration createConfiguration(List<ConditionsListItem> items, List<String> domains, String depth);

    /**
     * Combines list of {@link Matcher} objects into one {@link Matcher} object
     *
     * @param matchers - list of {@link Matcher} objects to combine into one {@link Matcher}
     * @return new {@link Matcher} object
     */
    Matcher combineMatchers(List<Matcher> matchers);

    /**
     * Parses list of {@link ConditionsListItem} into list of {@link Matcher}
     * using {@link #parseCondition(ConditionsListItem)} method
     *
     * @param items list of {@link ConditionsListItem} objects
     * @return list of {@link Matcher} objects
     */
    List<Matcher> parseConditions(List<ConditionsListItem> items);

    /**
     * Parse one {@link ConditionsListItem} object into {@link Matcher} object
     *
     * @param conditionItem - {@link ConditionsListItem} condition to be parsed into Matcher
     * @return new {@link Matcher} object
     */
    Matcher parseCondition(ConditionsListItem conditionItem);

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
     */
    Matcher parseSubCondition(String subCondition);

}