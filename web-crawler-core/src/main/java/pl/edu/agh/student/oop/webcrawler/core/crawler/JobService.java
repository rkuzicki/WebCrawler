package pl.edu.agh.student.oop.webcrawler.core.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * JobService is a {@link ExecutorService} wrapper used to execute the
 * {@link CrawlingJob} methods simultaneously.
 */

public class JobService {
    private final int THREADS = 8;
    private ExecutorService service = Executors.newFixedThreadPool(THREADS);

    JobService() {

    }

    public static JobService newJobService() {
        return new JobService();
    }

    public void add(Job job) {
        service.submit(() -> job.execute(this));
    }
}
