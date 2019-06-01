package com.tvajjala.drools.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Drools configuration
 *
 * @author ThirupathiReddy Vajjala
 */
@Configuration
public class DroolsConfig {

    private Logger LOG = LoggerFactory.getLogger(DroolsConfig.class);

    /**
     * @return kieContainer
     */
    @Bean
    public KieContainer kieContainer() {

        KieServices kieServices = KieServices.Factory.get();

        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);

        Resource dt = ResourceFactory.newClassPathResource("com/tvajjala/drools/drools_discount.xlsx");

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem()
                .write(dt);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem)
                .buildAll();

        LOG.info("DefaultReleaseId {} for results {}", kieBuilder.getKieModule().getReleaseId(), kieBuilder.getResults());

        KieRepository kieRepository = kieServices.getRepository();


        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        LOG.info("DefaultReleaseId {} ", krDefaultReleaseId);

        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());

        LOG.info("Created  kieContainer {} ", kieContainer);

        return kieContainer;


    }
}
