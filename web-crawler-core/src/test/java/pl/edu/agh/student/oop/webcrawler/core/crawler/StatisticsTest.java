package pl.edu.agh.student.oop.webcrawler.core.crawler;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class StatisticsTest {
    @Test
    void testSpeed() {
        Instant from = Instant.now();

        Statistics statistics = new Statistics();
        statistics.reportCrawled(100, Duration.ofSeconds(10));
        statistics.reportCrawled(30, Duration.ofSeconds(4));
        statistics.reportCrawled(50, Duration.ofSeconds(1));

        Statistics.Statistic statistic = statistics.getCrawlStatisticFrom(from);
        assertThat(statistic.speed()).isEqualTo(180d / 15d);
    }

    @Test
    void testSpeedFrom() throws InterruptedException {
        Statistics statistics = new Statistics();
        statistics.reportCrawled(100, Duration.ofSeconds(10));
        statistics.reportCrawled(30, Duration.ofSeconds(4));

        // we need to make sure that instants are not overlapping,
        // sleep at least the clock resolution
        Thread.sleep(100);
        Instant from = Instant.now();
        Thread.sleep(100);

        statistics.reportCrawled(50, Duration.ofSeconds(1));
        statistics.reportCrawled(70, Duration.ofSeconds(40));

        Statistics.Statistic statistic = statistics.getCrawlStatisticFrom(from);
        assertThat(statistic.speed()).isEqualTo(120d / 41d);
    }
}
