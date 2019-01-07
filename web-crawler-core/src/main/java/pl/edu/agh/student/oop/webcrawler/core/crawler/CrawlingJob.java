package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
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
 * {@link CrawlingJob} is a {@link Job} which crawls a single website -- searching for results and spawning child
 * jobs from found hyperlinks.
 */
class CrawlingJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(CrawlingJob.class);

    private CrawlingJobContext context;

    CrawlingJob(CrawlingJobContext context) {
        this.context = context;
    }

    /**
     * Method used to obtain valid addresses from a single web page.
     */
    private Stream<URI> links(Document doc) {
        Elements aElements = doc.select("a[href]");
        List<URI> links = new ArrayList<>();

        for (Element aElement : aElements) {
            String href = aElement.attr("abs:href");
            try {
                links.add(new URI(href));
            } catch (URISyntaxException e) {
                logger.info("Invalid link: " + href);
                logger.trace("Invalid link: " + href, e);
            }
        }

        return links.stream();
    }

    private CrawlingJob spawnChild(URI link) {
        logger.trace("Spawning a child job for: " + link);
        return new CrawlingJob(context.childContext(link));
    }

    @Override
    public void execute(JobService jobService) {
        CrawlingMode mode = context.currentCrawlingMode();
        logger.info("Crawling " + context.uri() +
                ", depth: " + context.currentDepth() +
                ", mode: " + mode);
        if (mode == CrawlingMode.NONE) {
            return;
        }

        Document doc;
        try {
            doc = Jsoup.connect(context.uri().toString()).get();
        } catch (IOException e) {
            logger.error("Cannot crawl: " + context.uri(), e);
            return;
        }

        Text websiteText = new HtmlParser(doc).parse();

        if (mode.followLinks()) {
            followLinks(jobService, doc);
        }

        if (mode.parse()) {
            parseWebsite(websiteText);
        }
    }

    private void parseWebsite(Text websiteText) {
        for (Sentence s : websiteText.getSentences()) {
            context.matchers().forEach(matcher -> {
                if (matcher.match(s)) {
                    logger.info("Matched sentence: " + s + ", at: " + context.uri());
                    context.configuration()
                            .matchListener()
                            .handleMatch(s, context.uri(), matcher);
                }
            });
        }
    }

    private void followLinks(JobService jobService, Document doc) {
        // spawn children
        if (context.currentDepth() < context.configuration().getDepth()) {
            links(doc).map(this::normalizeUri)
                    .filter(this::isTraversable)
                    .map(this::spawnChild)
                    .forEach(jobService::add);
        }
    }

    /**
     * Remove unimportant parts (for crawling) of a URI.
     */
    private URI normalizeUri(URI uri) {
        try {
            return new URI(
                    uri.getScheme(),
                    uri.getAuthority(),
                    uri.getPath(),
                    uri.getQuery(),
                    null);
        } catch (URISyntaxException e) {
            // if it fails, return uri
            return uri;
        }
    }

    private boolean isTraversable(URI uri) {
        Configuration conf = context.configuration();
        String authority = uri.getAuthority();

        if (conf.getDomains().contains(authority)) return true;

        if (conf.areSubdomainsEnabled()) {
            for (String domain : conf.getDomains()) {
                if (authority.startsWith(domain)) {
                    return true;
                }
            }
        }

        logger.trace("Address " + uri + " is being ignored");
        return false;
    }
}
