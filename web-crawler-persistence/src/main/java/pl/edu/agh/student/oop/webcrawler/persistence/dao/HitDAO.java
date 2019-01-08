package pl.edu.agh.student.oop.webcrawler.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.student.oop.webcrawler.persistence.HibernateUtil;
import pl.edu.agh.student.oop.webcrawler.persistence.model.Hit;
import pl.edu.agh.student.oop.webcrawler.persistence.model.Matcher;

import java.util.List;

public class HitDAO {

    public HitDAO() {}

    public void save(Hit[] hits) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (Hit hit: hits) {
            session.save(hit);
        }
        tx.commit();
    }

    public void save(Hit hit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(hit);
        tx.commit();
    }

    @SuppressWarnings("unchecked")
    public List<Hit> getHitsByMatcher(Matcher matcher) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Hit> hits = session.createNamedQuery("get_hits_by_matcher")
                .setParameter("matcher", matcher)
                .getResultList();
        tx.commit();
        session.close();
        return hits;

    }
}
