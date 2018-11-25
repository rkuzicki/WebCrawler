package pl.edu.agh.student.oop.webcrawler.core.matcher;

import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

public interface Matcher {
    boolean match(Text text);
}
