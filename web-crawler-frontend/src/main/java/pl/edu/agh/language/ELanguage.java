package pl.edu.agh.language;

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
