package pl.edu.agh.student.oop.webcrawler.persistence.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "check_hit_occurrence_in_db",
                query = "from DbHit where hitContext = :hitContext"
        )
})
@Entity
public class DbHit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hitContext;

    public DbHit() {

    }

    public DbHit(String hitContext) {
        this.hitContext = hitContext;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getHitContext() {
        return hitContext;
    }

    public void setHitContext(String hitContext) { this.hitContext = hitContext; }

    public String toString() {
        return this.id + " " + this.hitContext;
    }
}
