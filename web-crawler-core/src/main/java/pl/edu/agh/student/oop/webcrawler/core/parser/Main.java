package pl.edu.agh.student.oop.webcrawler.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.edu.agh.student.oop.webcrawler.core.parser.HTMLParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.filmweb.pl/film/Bokser-1997-18").get();
            HTMLParser htmlParser = new HTMLParser(doc);
            System.out.println(htmlParser.parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
