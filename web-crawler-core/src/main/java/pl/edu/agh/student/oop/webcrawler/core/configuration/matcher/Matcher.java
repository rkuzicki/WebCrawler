package pl.edu.agh.student.oop.webcrawler.core.configuration.matcher;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Text;

public interface Matcher {
    boolean match(Text text);
}
