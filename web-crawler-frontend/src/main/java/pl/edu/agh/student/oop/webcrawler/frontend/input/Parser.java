package pl.edu.agh.student.oop.webcrawler.frontend.input;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.configuration.ConfigurationBuilder;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.matcher.MatcherCompiler;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

import java.util.List;

public class Parser {

    private static final String WILD_CARD = "*";

    public static Configuration createConfiguration(String depth, List<String> domains, List<ConditionsListItem> conditions) {


        MatcherCompiler matcher = Matcher.compiler().matchAny();
        //TODO input -> matcher parser

        ConfigurationBuilder builder = Configuration.builder();
        builder.matcher(matcher.compile())
               .domains(domains)
               .depth(Integer.parseInt(depth));

        return builder.build();
    }
}
