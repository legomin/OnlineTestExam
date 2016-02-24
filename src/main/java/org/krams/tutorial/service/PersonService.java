package org.krams.tutorial.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.krams.tutorial.domain.Answer;
import org.krams.tutorial.domain.Person;
import org.krams.tutorial.domain.Question;
import org.krams.tutorial.domain.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service for processing Persons
 */
@Service("personService")
@Transactional
public class PersonService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * User servise functions
     */
    public Person getUser(String login, String pass) {

        if (login == null || pass == null || login.isEmpty()) {
            return null;
        }

        Session session = sessionFactory.getCurrentSession();
        StringBuilder query = new StringBuilder("from Person ");

        query.append(" where login = '" + login + "'");
        query.append(" and pass = '" + pass + "'");

        List<Person> list = session.createQuery(query.toString()).list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * Test servise functions
     */
    public Test getTest() {

        Session session = sessionFactory.getCurrentSession();
        List<Test> list = session.createQuery("from Test ").list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * Question servise functions
     */
    public Question getQuestionByNumber(int number, int testId) {

        Session session = sessionFactory.getCurrentSession();
        List<Question> list = session.createQuery(" from Question where number = " + number + " and test_id = " + testId + " ").list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<Question> getListNumbersOfQuestion(int testId) {

        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Question.class)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("number"), "number"))
                .setResultTransformer(Transformers.aliasToBean(Question.class)).list();

    }

    public Question getQuestionByID(int id) {
        Session session = sessionFactory.getCurrentSession();
        Question question = (Question) session.get(Question.class, id);
        return question;
    }

    /**
     * Answer servise functions
     */
    public List<Answer> getListAnswersByQuestionId(int questionId) {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" from Answer where question_id = " + questionId + " ").list();
    }


    public void saveUserScore(int score, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person existingPerson = (Person) session.get(Person.class, person.getId());
        existingPerson.setTestAssessment((double) score);

        session.save(existingPerson);
    }
}
