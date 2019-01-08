package pl.edu.agh.student.oop.webcrawler.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.student.oop.webcrawler.persistence.HibernateUtil;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbHit;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbMatcher;

import java.util.List;

public class DbHitDao {

    public DbHitDao() {}

    public void save(DbHit[] dbHits) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (DbHit dbHit : dbHits) {
            session.save(dbHit);
        }
        tx.commit();
    }

    public void save(DbHit dbHit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(dbHit);
        tx.commit();
    }

    @SuppressWarnings("unchecked")
    public List<DbHit> getHitsByMatcher(DbMatcher dbMatcher) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<DbHit> dbHits = session.createNamedQuery("get_hits_by_matcher")
                .setParameter("matcher", dbMatcher)
                .getResultList();
        tx.commit();
        session.close();
        return dbHits;

    }
}
