package pl.edu.agh.student.oop.webcrawler.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ConfigurationPersistence {
    private Config config;

    public ConfigurationPersistence(Config config) {
        this.config = config;
    }

    public void persistConfiguration(Session session) {
        Transaction tx = session.beginTransaction();
        session.save(config);
        tx.commit();
    }

}
