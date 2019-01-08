package pl.edu.agh.student.oop.webcrawler.persistence.model;

import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "get_configuration_by_id",
                query = "from Configuration where id = :id"
        )
})
@Entity
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ElementCollection
    private List<String> domains;
    @ElementCollection
    private List<String> startingPoints;
    private int depth;
    private boolean subdomains;

    @ManyToOne
    private Matcher matcher;

    public Configuration(List<String> domains, List<String> startingPoints, int depth, boolean subdomains, Matcher matcher) {
        this.domains = domains;
        this.startingPoints = startingPoints;
        this.depth = depth;
        this.subdomains = subdomains;
        this.matcher = matcher;
    }

    public Configuration() {

    }

    public long getId() {
        return this.id;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<String> getStartingPoints() {
        return startingPoints;
    }

    public void setStartingPoints(List<String> startingPoints) {
        this.startingPoints = startingPoints;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean subdomains() {
        return subdomains;
    }

    public void setSubdomains(boolean subdomains) {
        this.subdomains = subdomains;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public String toString() {
        return id
                + "\n" + domains
                + "\n" + startingPoints
                + "\n" + depth
                + "\n" + subdomains
                + "\n" + matcher;
    }
}
