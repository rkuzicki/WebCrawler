package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.OnStalledListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link JobService} is used to manage {@link Job} instances. It queues and executes them.
 */
public class JobService {
    private final AtomicInteger taskCount = new AtomicInteger(0);
    private final ExecutorService service;
    private final OnStalledListener listener;

    private JobService(int threads, OnStalledListener listener) {
        this.service = Executors.newFixedThreadPool(threads);
        this.listener = listener;
    }

    public static JobService newJobService(int threads, OnStalledListener listener) {
        return new JobService(threads, listener);
    }

    public void add(Job job) {
        taskCount.incrementAndGet();
        service.submit(() -> {
            job.execute(this);

            if (taskCount.decrementAndGet() == 0) {
                listener.stalled();
            }
        });
    }
}
