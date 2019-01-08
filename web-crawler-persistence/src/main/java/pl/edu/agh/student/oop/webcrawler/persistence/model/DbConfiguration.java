package pl.edu.agh.student.oop.webcrawler.persistence.model;

import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "get_configuration_by_id",
                query = "from DbConfiguration where id = :id"
        )
})
@Entity
public class DbConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ElementCollection
    private List<String> domains;
    @ElementCollection
    private List<String> startingPoints;
    private int depth;
    private boolean subdomains;

    @OneToMany
    private List<DbMatcher> dbMatchers;

    public DbConfiguration(List<String> domains, List<String> startingPoints, int depth, boolean subdomains, List<DbMatcher> dbMatchers) {
        this.domains = domains;
        this.startingPoints = startingPoints;
        this.depth = depth;
        this.subdomains = subdomains;
        this.dbMatchers = dbMatchers;
    }

    public DbConfiguration() {

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

    public List<DbMatcher> getDbMatchers() {
        return dbMatchers;
    }

    public void setDbMatchers(List<DbMatcher> dbMatcher) {
        this.dbMatchers = dbMatcher;
    }

    public String toString() {
        return id
                + "\n" + domains
                + "\n" + startingPoints
                + "\n" + depth
                + "\n" + subdomains
                + "\n" + dbMatchers;
    }
}
