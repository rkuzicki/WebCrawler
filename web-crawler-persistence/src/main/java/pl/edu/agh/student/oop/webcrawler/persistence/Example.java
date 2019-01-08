package pl.edu.agh.student.oop.webcrawler.persistence;

import pl.edu.agh.student.oop.webcrawler.persistence.dao.ConfigurationDAO;
import pl.edu.agh.student.oop.webcrawler.persistence.dao.HitDAO;
import pl.edu.agh.student.oop.webcrawler.persistence.dao.MatcherDAO;
import pl.edu.agh.student.oop.webcrawler.persistence.model.Configuration;
import pl.edu.agh.student.oop.webcrawler.persistence.model.Hit;
import pl.edu.agh.student.oop.webcrawler.persistence.model.Matcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) throws IOException {
        Matcher matcher = new Matcher("ala ma kota");
        Hit hit = new Hit("ala ma kota i psa", matcher);
        Hit hit2 = new Hit("ala ma kota i psa 2", matcher);

        MatcherDAO matcherDAO = new MatcherDAO();
        matcherDAO.save(matcher);

        HitDAO hitDAO = new HitDAO();
        hitDAO.save(hit);
        hitDAO.save(hit2);
        System.out.println(hitDAO.getHitsByMatcher(matcher));

        List<String> domains = new ArrayList<>();
        List<String> sp = new ArrayList<>();
        domains.add("xyz");
        domains.add("abc");
        sp.add("xyz");
        sp.add("abc");

        Configuration configuration = new Configuration(
            domains,
            sp,
            2,
            true,
            matcher
        );

        ConfigurationDAO configurationDAO = new ConfigurationDAO();
        configurationDAO.save(configuration);
        System.out.println(configurationDAO.getById(configuration.getId()));


    }
}
