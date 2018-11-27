package pl.edu.agh.student.oop.webcrawler.frontend.language;

public enum ELanguage {
    POLISH("polski"),
    ENGLISH("english");

    private final String name;

    private ELanguage(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
