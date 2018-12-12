package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

public class Crawler {
    private final JobService service = JobService.newJobService(4);
    private final Configuration config;
    private final MatchListener listener;

    public Crawler(Configuration config, MatchListener listener) {
        this.config = config;
        this.listener = listener;
    }

    public void start() {
        service.add(StartCrawlingJob.create(config, listener));
    }
}
