package pl.edu.agh.student.oop.webcrawler.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.student.oop.webcrawler.persistence.HibernateUtil;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbConfiguration;

public class DbConfigurationDao {

    public DbConfigurationDao() {}

    public void save(DbConfiguration config) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(config);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public DbConfiguration getById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        DbConfiguration config = (DbConfiguration)session.createNamedQuery("get_configuration_by_id")
                .setParameter("id", id)
                .getSingleResult();
        tx.commit();
        session.close();

        return config;
    }

}
