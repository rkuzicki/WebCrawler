package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

public class Crawler {
    private final JobService service = JobService.newJobService(4);
    private final Configuration config;

    public Crawler(Configuration config) {
        this.config = config;
    }

    public void start() {
        service.add(StartCrawlingJob.create(config));
    }
}
