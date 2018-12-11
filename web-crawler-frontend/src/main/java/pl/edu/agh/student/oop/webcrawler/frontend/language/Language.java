package pl.edu.agh.student.oop.webcrawler.frontend.language;

public enum Language {
    POLISH("polski"),
    ENGLISH("english");

    private final String name;

    private Language(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
