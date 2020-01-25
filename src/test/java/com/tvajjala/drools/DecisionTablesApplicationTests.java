package com.tvajjala.drools;

import com.tvajjala.drools.model.Customer;
import com.tvajjala.drools.model.Offer;
import com.tvajjala.drools.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test cases to evaluate the rules
 *
 * @author ThirupathiReddy Vajjala
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DecisionTablesApplicationTests {

    @Autowired
    private KieContainer kieContainer;


    @Test
    public void studentGradeTest() {

        final KieSession kieSession = kieContainer.newKieSession();
        final Student student = new Student();
        student.setMarks(900);
        student.setAttendance(70.5);
        kieSession.insert(student);
        kieSession.getAgenda().getAgendaGroup("GRADE").setFocus();
        kieSession.fireAllRules();

        Assert.assertEquals(Student.Grade.C, student.getGrade());
    }


    @Test
    public void customerFinancialPackageTest() {

        final KieSession kieSession = kieContainer.newKieSession();
        final Customer customer = new Customer();
        customer.setLifeStage(Customer.CustomerLifeStage.CAREERFOCUSED);
        customer.setAssets(Customer.CustomerAssets.FROM150KTO300K);
        customer.addNeed(Customer.CustomerNeed.LIFEINSURANCE);
        customer.addNeed(Customer.CustomerNeed.SAVINGACCOUNT);
        customer.addNeed(Customer.CustomerNeed.MORTAGE);
        kieSession.insert(customer);

        // Now we add the global variable which we use to communicate back our
        final Offer offer = new Offer();
        kieSession.setGlobal("offer", offer);
        kieSession.fireAllRules();

        Assert.assertEquals(10, offer.getDiscount());
        Assert.assertEquals(Offer.ProductPackage.CAREERFOCUSED_PACKAGE, offer.getFinancialPackage());

        Assert.assertArrayEquals(new Offer.Product[]{Offer.Product.INSURANCE, Offer.Product.LOAN}, offer.getProducts().toArray());
    }

}
