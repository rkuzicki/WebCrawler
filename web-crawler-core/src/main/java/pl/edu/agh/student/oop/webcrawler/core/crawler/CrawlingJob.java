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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class CrawlingJob implements Job {
    private CrawlingJobContext context;
    private JobService jobService;

    CrawlingJob(CrawlingJobContext context, JobService jobService) {
        this.context = context;
        this.jobService = jobService;
    }

    private Stream<URI> links(Document doc) {
        Elements aElements = doc.select("a[href]");
        List<URI> links = new ArrayList<>();
        for (Element aElement : aElements) {
            try {
                links.add(new URI(aElement.attr("abs:href")));
            } catch (URISyntaxException e) {
                System.out.println("Bad link");
            }
        }
        return links.stream();
    }

    private CrawlingJob spawnChild(URI link) {
        return new CrawlingJob(context.childContext(link), jobService);
    }

    public void execute() {
        try {
            Document doc = Jsoup.connect(context.url().toString()).get();
            Text websiteText = new HtmlParser(doc).parse();

            links(doc).map(this::spawnChild).forEach(jobService::add);

            for (Sentence s : websiteText.getSentences()) {
                if (context.matcher().match(s)) {
                    context.matchListener().handleMatch(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

