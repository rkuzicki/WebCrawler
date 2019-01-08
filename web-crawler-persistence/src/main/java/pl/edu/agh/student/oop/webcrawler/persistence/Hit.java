package pl.edu.agh.student.oop.webcrawler.persistence;


import javax.persistence.*;

@Entity
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hitContext;

    @ManyToOne
    private Matcher matchers;

    public Hit() {

    }

    public Hit(String hitContext, Matcher matchers) {
        this.hitContext = hitContext;
        this.matchers = matchers;
    }

    public Long getId() {
        return id;
    }

    public String getHitContext() {
        return hitContext;
    }
}
