package pl.edu.agh.student.oop.webcrawler.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.student.oop.webcrawler.persistence.HibernateUtil;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbHit;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbMatcher;

import java.util.ArrayList;
import java.util.List;

public class DbHitDao {

    public DbHitDao() {}

    public static void save(List<DbHit> dbHits) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (DbHit dbHit : dbHits) {
            session.save(dbHit);
        }
        tx.commit();
        session.close();
    }

    public static void save(DbHit dbHit) {
        List<DbHit> list = new ArrayList<>();
        list.add(dbHit);
        save(list);
    }

    public static boolean isInDatabase(String hitContext) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        List result = session.getNamedQuery("check_hit_occurrence_in_db")
                .setParameter("hitContext", hitContext)
                .getResultList();

        tx.commit();
        session.close();
        if(result.isEmpty()) return false;
        else return true;
    }

}
