package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.parser.HtmlParser;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class Job implements Callable<Object>
{
    public String pageUrl;
    private JobConfiguration jobConfiguration;
    private MatchListener matchListener;
    private JobService jobService;

    public Job(String url, JobConfiguration jobConfiguration, MatchListener matchListener, JobService jobService)
    {
        this.pageUrl = url;
        this.jobConfiguration = jobConfiguration;
        this.matchListener = matchListener;
        this.jobService = jobService;
    }

    private Matcher currentMatcher() {
        return jobConfiguration.matcher();
    }

    public List<Job> getLinks(Document doc) {
        Elements links = doc.select("a[href]");
        List<Job> jobList= new LinkedList<>();
        for (Element link: links) {
            System.out.println("Got " + link.attr("abs:href") + " depth : " + jobConfiguration.depth());
            jobList.add(new Job(
                    link.attr("abs:href"),
                    new JobConfiguration(this.jobConfiguration.matcher(), this.jobConfiguration.depth() + 1 , this.jobConfiguration.areSubdomains()),
                    this.matchListener,
                    this.jobService));
        }
        return jobList;
    }

    @Override
    public synchronized Object call() {
        try {
            Document doc = Jsoup.connect(pageUrl).get();
            Text websiteText = new HtmlParser(doc).parse();
            jobService.add(getLinks(doc));
            for (Sentence s: websiteText.getSentences()) {
                if (currentMatcher().match(s)) {
                    matchListener.hit();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

