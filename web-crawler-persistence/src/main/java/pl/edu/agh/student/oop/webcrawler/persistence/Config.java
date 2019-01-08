package pl.edu.agh.student.oop.webcrawler.persistence;

import javax.persistence.*;

@Entity
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private  String[] domains;
    private String[] startingPoints;
    private int depth;
    private boolean subdomains;

    @ManyToOne
    private Matcher matcher;

    public Config(String[] domains, String[] startingPoints, int depth, boolean subdomains, Matcher matcher) {
        this.domains = domains;
        this.startingPoints = startingPoints;
        this.depth = depth;
        this.subdomains = subdomains;
        this.matcher = matcher;
    }

    public Config() {

    }


}
