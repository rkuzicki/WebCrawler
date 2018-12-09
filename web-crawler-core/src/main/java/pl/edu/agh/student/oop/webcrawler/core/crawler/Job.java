package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.parser.HtmlParser;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

import java.io.IOException;
import java.util.concurrent.Callable;

public class Job implements Callable<Object>
{
    private String pageUrl;
    private JobConfiguration jobConfiguration;
    private MatchListener matchListener;

    public Job(String url) {
        this.pageUrl = url;
    }

    private Matcher currentMatcher() {
        return jobConfiguration.matcher();
    }

    @Override
    public Object call() {
        try {
            Document doc = Jsoup.connect(pageUrl).get();
            Text websiteText = new HtmlParser(doc).parse();
            for (Sentence s: websiteText.getSentences()) {
                if (currentMatcher().match(s)) {
                    matchListener.hit();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

