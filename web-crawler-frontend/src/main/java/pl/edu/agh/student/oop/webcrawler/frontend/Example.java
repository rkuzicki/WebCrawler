package pl.edu.agh.student.oop.webcrawler.frontend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.parser.HtmlParser;
import pl.edu.agh.student.oop.webcrawler.core.parser.ImmutableText;
import pl.edu.agh.student.oop.webcrawler.core.parser.Word;

import java.io.IOException;
import java.net.URL;

public class Example {
    public static void main(String[] args) throws IOException {
        String urlInput = "http://home.agh.edu.pl/~kzajac/dydakt/tw/zasady.html";
        URL url = new URL(urlInput);
        Document parsed = Jsoup.parse(url, 10000);

        ImmutableText websiteText = new HtmlParser(parsed).parse();
        System.out.println(websiteText);

        match1(websiteText);
        match2(websiteText);
    }

    private static void match1(ImmutableText websiteText) {
        System.out.println("# Matcher 1");

        Matcher matcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("na"))
                .thenMatch(Word.of("zajeciach"))
                .thenSkipUpTo(3)
                .thenMatch(Word.of("plusy"))
                .compile();

        websiteText.sentences().forEach(sentence -> {
            if (matcher.match(sentence)) {
                System.out.print("[Matched!] ");
            }

            System.out.println(sentence);
        });
    }

    private static void match2(ImmutableText websiteText) {
        System.out.println("# Matcher 2");

        Matcher matcher = Matcher.compiler()
                .thenMatchAny()
                .thenMatch(Word.of("spoznionym"))
                .thenMatch(Word.of("wiecej"))
                .thenSkipUpTo(3)
                .thenMatch(Word.of("niz"))
                .compile();

        websiteText.sentences().forEach(sentence -> {
            if (matcher.match(sentence)) {
                System.out.print("[Matched!] ");
            }

            System.out.println(sentence);
        });
    }
}
