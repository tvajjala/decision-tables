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
 * Drools configuration to create KieContainer as a spring bean
 *
 * @author ThirupathiReddy Vajjala
 */
@Configuration
public class DroolsConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(DroolsConfig.class);

    /**
     * @return kieContainer
     */
    @Bean
    public KieContainer kieContainer() throws Exception {

        final KieServices kieServices = KieServices.Factory.get();
        final DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLSX);

        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        final KieFileSystem fileSystem = kieServices.newKieFileSystem();

        Arrays.stream(resolver.getResources("classpath*:com/tvajjala/drools/*.xlsx")).forEach(
                resource -> {
                    try {
                        LOGGER.info("Reading file {}", resource.getFile());
                        fileSystem.write(ResourceFactory.newFileResource(resource.getFile()));
                    } catch (final IOException e) {
                        LOGGER.error("Error loading file", e);
                    }
                }
        );


        final KieBuilder kieBuilder = kieServices.newKieBuilder(fileSystem).buildAll();
        LOGGER.info("DefaultReleaseId {} for results {}", kieBuilder.getKieModule().getReleaseId(), kieBuilder.getResults());
        final KieRepository kieRepository = kieServices.getRepository();
        final ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        LOGGER.info("DefaultReleaseId {} ", krDefaultReleaseId);
        final KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        LOGGER.info("Created  kieContainer {} ", kieContainer);
        return kieContainer;

    }
}
