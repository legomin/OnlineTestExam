package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.krams.tutorial.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Виталий on 03.02.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)


public class TestTest {
    @Autowired
    private PersonService personService;

    @Test
    public void personTest() {
        org.krams.tutorial.domain.Test test = personService.getTest();
        Assert.assertEquals(test.getId(), "1");//existing user
    }
}
