package com.tvajjala.drools.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;

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
    public KieContainer kieContainer() throws Exception {

        KieServices kieServices = KieServices.Factory.get();

        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        KieFileSystem fileSystem = kieServices.newKieFileSystem();

        Arrays.stream(resolver.getResources("classpath*:com/tvajjala/drools/*.xlsx")).forEach(
                resource -> {
                    try {
                        LOG.info("Reading file {}", resource.getFile());
                        fileSystem.write(ResourceFactory.newFileResource(resource.getFile()));
                    } catch (IOException e) {
                        LOG.error("Error loading file", e);
                    }
                }
        );


        KieBuilder kieBuilder = kieServices.newKieBuilder(fileSystem).buildAll();

        LOG.info("DefaultReleaseId {} for results {}", kieBuilder.getKieModule().getReleaseId(), kieBuilder.getResults());

        KieRepository kieRepository = kieServices.getRepository();


        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        LOG.info("DefaultReleaseId {} ", krDefaultReleaseId);

        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());

        LOG.info("Created  kieContainer {} ", kieContainer);

        return kieContainer;


    }
}
