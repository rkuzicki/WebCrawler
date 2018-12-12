package pl.edu.agh.student.oop.webcrawler.frontend.util;

import java.time.Duration;

public class DurationPrettyPrinter {
    public String prettyPrintAgo(Duration duration) {
        long minutes = Math.abs(duration.toMinutes());
        long seconds = Math.abs(duration.getSeconds()) % 60;
        if (minutes > 10) {
            return minutes + "m ago";
        } else if (minutes > 0) {
            return minutes + "m " + seconds + "s ago";
        } else if (seconds > 5) {
            return seconds + "s ago";
        } else {
            return "just now";
        }
    }
}
