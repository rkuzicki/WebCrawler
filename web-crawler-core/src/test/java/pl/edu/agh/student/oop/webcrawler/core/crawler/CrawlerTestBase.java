package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.mockserver.integration.ClientAndServer;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.configuration.ConfigurationBuilder;

import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class CrawlerTestBase {
    private static final int PORT = 1080;
    private static final String DOMAIN = "localhost:" + PORT;
    private ClientAndServer mockServer;
    private Lock stalledLock = new ReentrantLock();
    private Condition stalledCondition = stalledLock.newCondition();

    String address() {
        return "http://" + DOMAIN;
    }

    ClientAndServer server() {
        return mockServer;
    }

    String wrapWithBody(String content) {
        return "<html><body>" + content + "</body></html>";
    }

    void startMockServer() {
        mockServer = startClientAndServer(PORT);
    }

    void stopMockServer() {
        mockServer.stop();
    }

    void registerEndpoint(String path, String content){
        server().when(request().withPath(path))
                .respond(response().withBody(wrapWithBody(content)));
    }

    ConfigurationBuilder basicConfiguration() {
        return Configuration.builder()
                .domains(Collections.singletonList(DOMAIN))
                .threads(1)
                .whenStalled(() -> {
                    stalledLock.lock();
                    try {
                        stalledCondition.signalAll();
                    } finally {
                        stalledLock.unlock();
                    }
                });
    }

    void waitForCrawler() throws InterruptedException {
        stalledLock.lock();
        try {
            stalledCondition.await();
        } finally {
            stalledLock.unlock();
        }
    }
}
