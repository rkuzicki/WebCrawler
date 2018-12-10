package pl.edu.agh.student.oop.webcrawler.frontend.input;

import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.matcher.MatcherCompiler;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

public class Parser {

    private static final String WILD_CARD = "*";

    Matcher parse(String input) {
        MatcherCompiler matcher = Matcher.compiler().matchAny();
        int wildCardsCounter = 0;
        for (String string : input.split(" ")) {
            if(string.equals(WILD_CARD)) {
                wildCardsCounter++;
            }
            else if(wildCardsCounter > 0){
                matcher.thenSkip(wildCardsCounter);
                matcher.thenMatch(Word.of(string));
                wildCardsCounter = 0;
            }
            else {
                matcher.thenMatch(Word.of(string));
            }
        }

        return matcher.compile();
    }
}
