package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

public class Crawler {
    private final JobService service;
    private final Configuration config;

    public Crawler(Configuration config) {
        this.config = config;
        this.service = JobService.newJobService(4, config.onStalledListener());
    }

    public void start() {
        service.add(StartCrawlingJob.create(config));
    }
}
