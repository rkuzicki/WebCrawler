package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

public class Crawler {
    private final Statistics statistics = new Statistics();

    private final JobService service;
    private final Configuration config;

    public Crawler(Configuration config) {
        this.config = config;
        this.service = JobService.newJobService(config.threads(), config.onStalledListener());
    }

    public void start() {
        service.add(StartCrawlingJob.create(this, config));
    }

    public Statistics statistics() {
        return statistics;
    }
}
