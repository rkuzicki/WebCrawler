package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

class StartCrawlingJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(StartCrawlingJob.class);

    private Crawler parent;
    private Configuration config;

    static StartCrawlingJob create(Crawler parent, Configuration config) {
        return new StartCrawlingJob(parent, config);
    }

    private StartCrawlingJob(Crawler parent, Configuration config) {
        this.parent = parent;
        this.config = config;
    }

    @Override
    public void execute(JobService jobService) {
        logger.info("Starting crawling for: " + config.getStartingPoints());
        config.getStartingPoints().stream()
                .map(uri -> CrawlingJobContext.rootContext(config, uri))
                .map(context -> new CrawlingJob(parent, context))
                .forEach(jobService::add);
    }
}
