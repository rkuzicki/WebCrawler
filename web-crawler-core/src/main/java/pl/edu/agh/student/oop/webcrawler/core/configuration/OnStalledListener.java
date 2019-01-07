package pl.edu.agh.student.oop.webcrawler.core.configuration;

@FunctionalInterface
public interface OnStalledListener {
    void stalled();
}
