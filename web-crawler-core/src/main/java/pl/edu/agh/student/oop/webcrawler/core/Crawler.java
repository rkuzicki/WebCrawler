package pl.edu.agh.student.oop.webcrawler.core;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.crawler.JobService;
import pl.edu.agh.student.oop.webcrawler.core.crawler.StartCrawlingJob;

public class Crawler {
    private final JobService service = JobService.newJobService();
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
