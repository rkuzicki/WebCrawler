package pl.edu.agh.student.oop.webcrawler.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.student.oop.webcrawler.persistence.HibernateUtil;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbMatcher;

import java.util.ArrayList;
import java.util.List;

public class DbMatcherDao {

    public DbMatcherDao() {}

    public static void save(List<DbMatcher> dbMatchers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for(DbMatcher dbMatcher : dbMatchers) {
            session.save(dbMatcher);
        }
        tx.commit();
        session.close();
    }

    public static void save(DbMatcher dbMatcher) {
        List<DbMatcher> list = new ArrayList<>();
        list.add(dbMatcher);
        save(list);
    }
}
