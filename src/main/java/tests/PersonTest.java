package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.krams.tutorial.domain.Person;
import org.krams.tutorial.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Виталий on 03.02.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)

public class PersonTest {

    @Autowired
    private PersonService personService;

    @Test
    public void personTest() {

        Person person = personService.getUser("user1", "pass1");
        Assert.assertEquals(person.getLogin(), "user1");//existing user

        person = personService.getUser("", "pass1");
        Assert.assertEquals(null, person);

        person = personService.getUser("user1", "");
        Assert.assertEquals(null, person);

        person = personService.getUser("user1", null);
        Assert.assertEquals(null, person);

    }
}
