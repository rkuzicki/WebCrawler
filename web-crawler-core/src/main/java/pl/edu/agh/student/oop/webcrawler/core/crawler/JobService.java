package pl.edu.agh.student.oop.webcrawler.core.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class JobService {
    private final int THREADS = 8;
    private ExecutorService service = Executors.newFixedThreadPool(THREADS);

    JobService() {

    }

    public void add(Job job) {
        service.submit(job::execute);
    }
}