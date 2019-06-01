package com.tvajjala.drools;

import com.tvajjala.drools.model.Customer;
import com.tvajjala.drools.model.Offer;
import com.tvajjala.drools.model.Student;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    private Logger LOG = LoggerFactory.getLogger(Application.class);
    @Autowired
    private KieContainer kieContainer;

    @Override
    public void run(String... args) {

        KieSession kieSession = kieContainer.newKieSession();

        Student student = new Student();
        student.setMarks(900);
        student.setAttendance(90.00);
        kieSession.insert(student);
        kieSession.fireAllRules();

        LOG.info("Student Received Grade  {} ", student.getGrade());

    }


    public void customer() {

        KieSession kieSession = kieContainer.newKieSession();
        Customer customer = new Customer();
        customer.setLifeStage(Customer.CustomerLifeStage.CAREERFOCUSED);
        customer.setAssets(Customer.CustomerAssets.FROM150KTO300K);
        customer.addNeed(Customer.CustomerNeed.LIFEINSURANCE);
        customer.addNeed(Customer.CustomerNeed.SAVINGACCOUNT);
        customer.addNeed(Customer.CustomerNeed.MORTAGE);
        kieSession.insert(customer);
        // Now we add the global variable which we use to communicate back our
        Offer offer = new Offer();
        kieSession.setGlobal("offer", offer);
        kieSession.fireAllRules();


        System.out.println(offer.getDiscount() == 10);

        System.out.println(offer.getFinancialPackage() == Offer.ProductPackage.CAREERFOCUSED_PACKAGE);
    }
}
