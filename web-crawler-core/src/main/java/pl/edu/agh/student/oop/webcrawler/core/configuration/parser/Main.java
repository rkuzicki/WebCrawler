package pl.edu.agh.student.oop.webcrawler.core.configuration.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
