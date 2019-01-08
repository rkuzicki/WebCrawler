package pl.edu.agh.student.oop.webcrawler.persistence;

import pl.edu.agh.student.oop.webcrawler.persistence.dao.DbConfigurationDao;
import pl.edu.agh.student.oop.webcrawler.persistence.dao.DbHitDao;
import pl.edu.agh.student.oop.webcrawler.persistence.dao.DbMatcherDao;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbConfiguration;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbHit;
import pl.edu.agh.student.oop.webcrawler.persistence.model.DbMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) throws IOException {
        DbMatcher dbMatcher = new DbMatcher("ala ma kota");
        DbHit dbHit = new DbHit("ala ma kota i psa", dbMatcher);
        DbHit dbHit2 = new DbHit("ala ma kota i psa 2", dbMatcher);

        DbMatcherDao dbMatcherDao = new DbMatcherDao();
        dbMatcherDao.save(dbMatcher);

        DbHitDao dbHitDao = new DbHitDao();
        dbHitDao.save(dbHit);
        dbHitDao.save(dbHit2);
        System.out.println(dbHitDao.getHitsByMatcher(dbMatcher));

        List<String> domains = new ArrayList<>();
        List<String> sp = new ArrayList<>();
        List<DbMatcher> dbMatchers = new ArrayList<>();
        domains.add("xyz");
        domains.add("abc");
        sp.add("xyz");
        sp.add("abc");
        dbMatchers.add(dbMatcher);

        DbConfiguration dbConfiguration = new DbConfiguration(
            domains,
            sp,
            2,
            true,
                dbMatchers
        );

        DbConfigurationDao dbConfigurationDao = new DbConfigurationDao();
        dbConfigurationDao.save(dbConfiguration);
        System.out.println(dbConfigurationDao.getById(dbConfiguration.getId()));


    }
}
