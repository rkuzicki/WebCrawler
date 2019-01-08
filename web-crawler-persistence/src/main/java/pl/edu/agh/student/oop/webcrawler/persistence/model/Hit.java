package pl.edu.agh.student.oop.webcrawler.persistence.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "get_hits_by_matcher",
                query = "from Hit h where h.matcher = :matcher"
        )
})
@Entity
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hitContext;

    @ManyToOne
    private Matcher matcher;

    public Hit() {

    }

    public Hit(String hitContext, Matcher matcher) {
        this.hitContext = hitContext;
        this.matcher = matcher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getHitContext() {
        return hitContext;
    }

    public void setHitContext(String hitContext) { this.hitContext = hitContext; }

    public Matcher getMatcher() { return this.matcher; }

    public void setMatcher(Matcher matcher) { this.matcher = matcher; }

    public String toString() {
        return this.id + " " + this.hitContext;
    }
}
