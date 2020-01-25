package com.tvajjala.drools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Demonstrating Drools Decision tables to write business rules
 * <p>
 * Refer https://docs.jboss.org/drools/release/5.2.0.Final/drools-expert-docs/html/ch06.html for detailed documentation on drools decision tables.
 *
 * @author ThirupathiReddy Vajjala
 */
@SpringBootApplication
public class Application implements CommandLineRunner {


    private final Logger LOG = LoggerFactory.getLogger(Application.class);


    public static void main(final String[] args) {

        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(final String... args) {
        LOG.info("Application started...");
    }
}
