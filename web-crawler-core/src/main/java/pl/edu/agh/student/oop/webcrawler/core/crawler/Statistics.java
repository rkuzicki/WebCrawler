package pl.edu.agh.student.oop.webcrawler.core.crawler;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

public class Statistics {
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
            return (double) sum.size / sum.duration.getSeconds();
        }
    }

    private NavigableMap<Instant, Entry> downloadStatistics = new TreeMap<>();
    private NavigableMap<Instant, Entry> crawlStatistics = new TreeMap<>();

    public Statistic getDownloadStatisticFrom(Instant from) {
        return new Statistic(downloadStatistics.tailMap(from).values());
    }

    public Statistic getCrawlStatisticFrom(Instant from) {
        return new Statistic(crawlStatistics.tailMap(from).values());
    }

    void reportDownloaded(long size, Duration duration) {
        downloadStatistics.put(Instant.now(), new Entry(size, duration));
    }

    void reportCrawled(long size, Duration duration) {
        crawlStatistics.put(Instant.now(), new Entry(size, duration));
    }

    void free(Instant till) {
        downloadStatistics.headMap(till).clear();
        crawlStatistics.headMap(till).clear();
    }
}
