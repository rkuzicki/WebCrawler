package pl.edu.agh.student.oop.webcrawler.core.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link JobService} is used to manage {@link Job} instances. It queues and executes them.
 */
public class JobService {
    private ExecutorService service;

    private JobService(int threads) {
        service = Executors.newFixedThreadPool(threads);
    }

    public static JobService newJobService(int threads) {
        return new JobService(threads);
    }

    public void add(Job job) {
        service.submit(() -> job.execute(this));
    }
}
