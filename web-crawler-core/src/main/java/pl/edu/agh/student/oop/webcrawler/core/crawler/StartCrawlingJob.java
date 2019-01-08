package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

class StartCrawlingJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(StartCrawlingJob.class);

    private Configuration config;

    public static StartCrawlingJob create(Configuration config) {
        return new StartCrawlingJob(config);
    }

    private StartCrawlingJob(Configuration config) {
        this.config = config;
    }

    @Override
    public void execute(JobService jobService) {
        logger.info("Starting crawling for: " + config.getStartingPoints());
        config.getStartingPoints().stream()
                .map(uri -> CrawlingJobContext.rootContext(config, uri))
                .map(CrawlingJob::new)
                .forEach(jobService::add);
    }
}
