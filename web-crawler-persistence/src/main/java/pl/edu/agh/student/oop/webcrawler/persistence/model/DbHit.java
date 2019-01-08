package pl.edu.agh.student.oop.webcrawler.persistence.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "get_hits_by_matcher",
                query = "from DbHit h where h.dbMatcher = :matcher"
        )
})
@Entity
public class DbHit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hitContext;

    @ManyToOne
    private DbMatcher dbMatcher;

    public DbHit() {

    }

    public DbHit(String hitContext, DbMatcher dbMatcher) {
        this.hitContext = hitContext;
        this.dbMatcher = dbMatcher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getHitContext() {
        return hitContext;
    }

    public void setHitContext(String hitContext) { this.hitContext = hitContext; }

    public DbMatcher getDbMatcher() { return this.dbMatcher; }

    public void setDbMatcher(DbMatcher dbMatcher) { this.dbMatcher = dbMatcher; }

    public String toString() {
        return this.id + " " + this.hitContext;
    }
}
