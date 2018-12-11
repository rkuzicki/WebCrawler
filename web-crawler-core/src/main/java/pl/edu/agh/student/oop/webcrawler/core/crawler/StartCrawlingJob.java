package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.MatchListener;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

public class StartCrawlingJob implements Job {
    private Configuration config;
    private MatchListener listener;

    public static StartCrawlingJob create(Configuration config, MatchListener listener) {
        return new StartCrawlingJob(config, listener);
    }

    public StartCrawlingJob(Configuration config, MatchListener listener) {
        this.config = config;
        this.listener = listener;
    }

    @Override
    public void execute(JobService jobService) {
        config.getStartingPoints().stream()
                .map(uri -> CrawlingJobContext.rootContext(config, listener, uri))
                .map(CrawlingJob::new)
                .forEach(jobService::add);
    }
}
