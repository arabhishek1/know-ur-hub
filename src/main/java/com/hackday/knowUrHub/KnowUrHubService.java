package com.hackday.knowUrHub;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hackday.knowUrHub.core.configurations.KnowUrHubConfiguration;
import com.hackday.knowUrHub.core.guice.GuiceBundle;
import com.hackday.knowUrHub.health.KnowUrHubHealthCheck;
import com.hackday.knowUrHub.modules.KnowUrHubClientModule;
import com.hackday.knowUrHub.utils.ObjectMapperProvider;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.json.ObjectMapperFactory;
import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.activejpa.jpa.JPA;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class KnowUrHubService extends Service<KnowUrHubConfiguration> {

    public static void main(String[] args) throws Exception {
        new KnowUrHubService().run(args);
    }

    @Override
    public void initialize(Bootstrap bootstrap) {
        GuiceBundle<KnowUrHubConfiguration> guiceBundle = GuiceBundle
                .<KnowUrHubConfiguration>newBuilder()
                .addModule(new KnowUrHubClientModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(KnowUrHubConfiguration.class).build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new AssetsBundle("/swagger-ui"));
        configureObjectMapperFactory(bootstrap);

    }

    private void configureObjectMapperFactory(Bootstrap bootstrap) {
        ObjectMapperFactory mapperFactory = bootstrap.getObjectMapperFactory();
        mapperFactory.setSerializationInclusion(Include.NON_EMPTY);
        mapperFactory.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapperFactory.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        mapperFactory.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapperFactory.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        ObjectMapperProvider.INSTANCE.set(mapperFactory.build());
    }

    @Override
    public void run(KnowUrHubConfiguration knowUrHubConfiguration, Environment environment) throws Exception {
        environment.addHealthCheck(new KnowUrHubHealthCheck("know_ur_hub"));
        ActiveJpaAgentLoader.instance().loadAgent();
        JPA.instance.addPersistenceUnit("know_ur_hub", knowUrHubConfiguration.getMysql().getKnow_ur_hub(), true);
        System.out.println("know-ur-hub config: " + knowUrHubConfiguration.toString());

        environment.addResource(new ApiListingResourceJSON());
        environment.addProvider(new ResourceListingProvider());
        environment.addProvider(new ApiDeclarationProvider());

        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion(knowUrHubConfiguration.getSwagger().getApiVersion());
        config.setBasePath(knowUrHubConfiguration.getSwagger().getBasePath());

    }
}
