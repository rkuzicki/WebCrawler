package pl.edu.agh.student.oop.webcrawler.core.crawler;

public interface CrawlerMonitor {
    void statisticsUpdated(Statistics current);

    static CrawlerMonitor empty() {
        return current -> {

        };
    }
}
