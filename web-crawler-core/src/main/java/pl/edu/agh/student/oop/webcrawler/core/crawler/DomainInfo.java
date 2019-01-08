package pl.edu.agh.student.oop.webcrawler.core.crawler;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class DomainInfo {
    private ConcurrentMap<URI, Integer> minDepths = new ConcurrentHashMap<>();

    void recordVisit(URI uri, int depth) {
        Integer last = minDepths.get(uri);
        if (last == null || last > depth) {
            minDepths.put(uri, depth);
        }
    }

    Optional<Integer> minVisitDepth(URI uri) {
        return Optional.ofNullable(minDepths.get(uri));
    }
}
