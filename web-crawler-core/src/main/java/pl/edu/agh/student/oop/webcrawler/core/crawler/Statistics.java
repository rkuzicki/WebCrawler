package pl.edu.agh.student.oop.webcrawler.core.crawler;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;

public class Statistics {
    private CrawlerMonitor monitor;

    Statistics() {
        this.monitor = CrawlerMonitor.empty();
    }

    Statistics(CrawlerMonitor monitor) {
        this.monitor = monitor;
    }

    private static class Entry {
        private final long size;
        private final Duration duration;

        private Entry(long size, Duration duration) {
            this.size = size;
            this.duration = duration;
        }

        static Entry zero() {
            return new Entry(0, Duration.ZERO);
        }

        static BinaryOperator<Entry> addition() {
            return (e1, e2) -> new Entry(e1.size + e2.size, e1.duration.plus(e2.duration));
        }
    }

    public class Statistic {
        private Collection<Entry> data;

        private Statistic(Collection<Entry> data) {
            this.data = data;
        }

        public double speed() {
            Entry sum = data.stream()
                    .reduce(Entry.zero(), Entry.addition());
            return (double) sum.size / durationToSeconds(sum.duration);
        }

        private double durationToSeconds(Duration duration) {
            return duration.getSeconds() + 1e-9 * duration.getNano();
        }
    }

    private final ConcurrentNavigableMap<Instant, Entry> downloadStatistics =
            new ConcurrentSkipListMap<>();
    private final ConcurrentNavigableMap<Instant, Entry> crawlStatistics =
            new ConcurrentSkipListMap<>();

    public Statistic getDownloadStatisticFrom(Instant from) {
        return new Statistic(downloadStatistics.tailMap(from).values());
    }

    public Statistic getCrawlStatisticFrom(Instant from) {
        return new Statistic(crawlStatistics.tailMap(from).values());
    }

    void reportDownloaded(long size, Duration duration) {
        synchronized (downloadStatistics) {
            reportOn(downloadStatistics, size, duration);
        }
    }

    void reportCrawled(long size, Duration duration) {
        synchronized (crawlStatistics) {
            reportOn(crawlStatistics, size, duration);
        }
    }

    private void reportOn(
            Map<Instant, Entry> statistics,
            long size, Duration duration) {
        Instant now = Instant.now();
        while (statistics.containsKey(now)) {
            now = now.plusNanos(1);
        }

        statistics.put(now, new Entry(size, duration));
        monitor.statisticsUpdated(this);
    }

    void free(Instant till) {
        downloadStatistics.headMap(till).clear();
        crawlStatistics.headMap(till).clear();
    }
}
