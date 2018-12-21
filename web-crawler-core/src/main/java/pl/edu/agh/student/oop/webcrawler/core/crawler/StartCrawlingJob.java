package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

class StartCrawlingJob implements Job {
    private Configuration config;

    public static StartCrawlingJob create(Configuration config) {
        return new StartCrawlingJob(config);
    }

    private StartCrawlingJob(Configuration config) {
        this.config = config;
    }

    @Override
    public void execute(JobService jobService) {
        config.getStartingPoints().stream()
                .map(uri -> CrawlingJobContext.rootContext(config, uri))
                .map(CrawlingJob::new)
                .forEach(jobService::add);
    }
}
