package pl.edu.agh.student.oop.webcrawler.core.crawler;

public enum CrawlingMode {
    FULL(true, true),
    FOLLOW_LINKS(true, false),
    NONE(false, false);

    private final boolean followLinks;
    private final boolean parse;

    CrawlingMode(boolean followLinks, boolean parse) {
        this.followLinks = followLinks;
        this.parse = parse;
    }

    public boolean followLinks() {
        return followLinks;
    }

    public boolean parse() {
        return parse;
    }
}
