package pl.edu.agh.student.oop.webcrawler.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Matcher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String matcher;

    public Matcher() {

    }

    public Matcher(String matcher) {
        this.matcher = matcher;
    }

}
