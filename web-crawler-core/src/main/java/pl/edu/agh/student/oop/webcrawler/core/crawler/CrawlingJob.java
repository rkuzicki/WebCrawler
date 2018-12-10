package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.student.oop.webcrawler.core.parser.HtmlParser;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.core.parser.Text;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class CrawlingJob implements Job
{
    private JobConfiguration jobConfiguration;
    private JobService jobService;

    public CrawlingJob(JobConfiguration jobConfiguration, JobService jobService)
    {
        this.jobConfiguration = jobConfiguration;
        this.jobService = jobService;
    }

    public List<CrawlingJob> getLinks(Document doc) {
        Elements links = doc.select("a[href]");
        List<CrawlingJob> jobList= new LinkedList<>();
        for (Element link: links) {
            try {
                URI url = new URI(link.attr("abs:href"));
                jobList.add(new CrawlingJob(
                        new JobConfiguration(
                                this.jobConfiguration.configuration(),
                                this.jobConfiguration.currentDepth() + 1,
                                this.jobConfiguration.matchListener(),
                                url),
                        this.jobService));
            } catch (URISyntaxException e) {
                System.out.println("Bad link");
            }
        }
        return jobList;
    }

    public void execute() {
        try {
            Document doc = Jsoup.connect(jobConfiguration.url().toString()).get();
            Text websiteText = new HtmlParser(doc).parse();
            jobService.add(getLinks(doc));
            for (Sentence s: websiteText.getSentences()) {
                if (jobConfiguration.matcher().match(s)) {
                    jobConfiguration.matchListener().hit(s);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

