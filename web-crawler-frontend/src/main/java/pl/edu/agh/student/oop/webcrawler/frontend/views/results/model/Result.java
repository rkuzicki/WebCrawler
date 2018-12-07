package pl.edu.agh.student.oop.webcrawler.frontend.views.results.model;

import org.immutables.value.Value;

import java.net.URL;
import java.time.LocalDateTime;

@Value.Immutable
public interface Result {
    static ImmutableResult.Builder builder() {
        return ImmutableResult.builder();
    }

    String getMatchedText();

    URL getSource();

    LocalDateTime getCreationDate();
}
