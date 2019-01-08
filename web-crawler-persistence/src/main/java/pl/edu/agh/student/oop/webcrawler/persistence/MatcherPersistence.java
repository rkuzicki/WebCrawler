package pl.edu.agh.student.oop.webcrawler.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class MatcherPersistence {
    private Matcher matcher;

    public MatcherPersistence(Matcher matcher) {
        this.matcher = matcher;
    }

    public void persistConfiguration(Session session) {
        Transaction tx = session.beginTransaction();
        session.save(matcher);
        tx.commit();
    }
}
