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

class CrawlingJob implements Job {
    private CrawlingJobContext context;

    CrawlingJob(CrawlingJobContext context) {
        this.context = context;
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
        return new CrawlingJob(context.childContext(link));
    }

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

