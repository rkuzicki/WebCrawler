package pl.edu.agh.student.oop.webcrawler.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DbMatcher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String matcher;

    public DbMatcher() {}

    public DbMatcher(String matcher) {
        this.matcher = matcher;
    }

}
