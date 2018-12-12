package pl.edu.agh.student.oop.webcrawler.core.crawler;

import javafx.application.Platform;
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

/**
 * CrawlingJob is a task which performs operations on a single website -
 * looking for links, spawning them to {@link JobService} and matching results.
 */
class CrawlingJob implements Job {
    private CrawlingJobContext context;

    CrawlingJob(CrawlingJobContext context) {
        this.context = context;
    }

    /**
     * Method used to obtain valid addresses from a single web page.
     * @param doc a {@link Document} representing the web page
     * @return the list of addresses
     */

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

    /**
     * Creates a new CrawlingJob from the given URI
     */

    private CrawlingJob spawnChild(URI link) {
        return new CrawlingJob(context.childContext(link));
    }

    /**
     * Main method for a single CrawlingJob task
     */
    @Override
    public void execute(JobService jobService) {
        Document doc;
        try {
            doc = Jsoup.connect(context.uri().toString()).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Text websiteText = new HtmlParser(doc).parse();

        links(doc).map(this::spawnChild)
                .forEach(jobService::add);

        for (Sentence s : websiteText.getSentences()) {
            if (context.matcher().match(s)) {
                Platform.runLater(
                        () -> context.matchListener().handleMatch(s, context.uri()));
            }
        }
    }
}

