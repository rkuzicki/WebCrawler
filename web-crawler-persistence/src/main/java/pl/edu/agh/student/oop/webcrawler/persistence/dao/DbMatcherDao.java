package pl.edu.agh.student.oop.webcrawler.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.student.oop.webcrawler.persistence.HibernateUtil;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbMatcher;

public class DbMatcherDao {

    public DbMatcherDao() {}

    public void save(DbMatcher dbMatcher) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(dbMatcher);
        tx.commit();
    }
}
