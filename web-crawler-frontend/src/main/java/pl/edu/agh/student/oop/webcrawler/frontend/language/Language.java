package pl.edu.agh.student.oop.webcrawler.frontend.language;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public enum Language {
    POLISH("polski", "pl_pl"),
    ENGLISH("english", "en_en");

    public static final Path LANG_CONFIG_FILE = Paths.get("lang.conf");

    private final String name;
    private final String locale;

    Language(String name, String locale) {
        this.name = name;
        this.locale = locale;
    }

    private static final Map<String, Language> map;
    static {
        map = new HashMap<>();
        for(Language l : Language.values()) {
            map.put(l.getName(), l);
        }
    }

    public String getName() {
        return this.name;
    }
    public String getLocale() { return this.locale; }
    public static String getLocaleByName(String name) { return map.get(name).getLocale(); }
}
