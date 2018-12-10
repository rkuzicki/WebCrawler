package pl.edu.agh.student.oop.webcrawler.core.crawler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobService {
    private final int THREADS = 8;
    private ExecutorService service = Executors.newFixedThreadPool(THREADS);

    public JobService() {

    }

    public void add(List<CrawlingJob> jobs) {
        for(CrawlingJob job: jobs) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    job.execute();
                }
            });
        }
    }
}
