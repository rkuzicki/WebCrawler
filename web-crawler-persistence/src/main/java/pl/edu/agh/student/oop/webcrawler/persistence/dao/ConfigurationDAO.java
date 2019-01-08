package pl.edu.agh.student.oop.webcrawler.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.student.oop.webcrawler.persistence.HibernateUtil;
import pl.edu.agh.student.oop.webcrawler.persistence.model.Configuration;
import pl.edu.agh.student.oop.webcrawler.persistence.model.Hit;

import java.util.List;

public class ConfigurationDAO {

    public ConfigurationDAO() {}

    public void save(Configuration config) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(config);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public Configuration getById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Configuration config = (Configuration)session.createNamedQuery("get_configuration_by_id")
                .setParameter("id", id)
                .getSingleResult();
        tx.commit();
        session.close();

        return config;
    }

}
