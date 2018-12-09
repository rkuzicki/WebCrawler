package pl.edu.agh.student.oop.webcrawler.core.crawler;

import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Crawler {
    private final int THREADS = 8;
    private Configuration configuration;
    private ExecutorService service;

    public Crawler(Configuration configuration) {
        this.configuration = configuration;
        this.service = Executors.newFixedThreadPool(THREADS);
    }

    public void start() {

    }


}
