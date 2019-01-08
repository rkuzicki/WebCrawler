package pl.edu.agh.student.oop.webcrawler.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HitPersistence {
    private Hit[] hits;

    public HitPersistence(Hit[] hits) {
        this.hits = hits;
    }

    public void persistHit(Session session) {
        Transaction tx = session.beginTransaction();
        for (Hit hit: hits) {
            session.save(hit);
        }
        tx.commit();
    }
}
