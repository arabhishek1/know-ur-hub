package com.hackday.knowUrHub.core.guice;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.yammer.dropwizard.Bundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.lifecycle.Managed;
import com.yammer.dropwizard.tasks.Task;
import com.yammer.metrics.core.HealthCheck;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Set;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class AutoConfig {

    private Reflections reflections;

    public AutoConfig(String... basePackages) {
        Preconditions.checkArgument(basePackages.length > 0);

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        FilterBuilder filterBuilder = new FilterBuilder();
        for (String basePkg : basePackages) {
            configurationBuilder.addUrls(ClasspathHelper.forPackage(basePkg));
            filterBuilder.include(FilterBuilder.prefix(basePkg));
        }

        configurationBuilder.filterInputsBy(filterBuilder).setScanners(
                new SubTypesScanner(), new TypeAnnotationsScanner());
        this.reflections = new Reflections(configurationBuilder);
    }

    public void run(Environment environment, Injector injector) {
        addHealthChecks(environment, injector);
        addProviders(environment, injector);
        addInjectableProviders(environment, injector);
        addResources(environment, injector);
        addTasks(environment, injector);
        addManaged(environment, injector);

    }

    public void initialize(Bootstrap<?> bootstrap, Injector injector) {
        addBundles(bootstrap, injector);
    }

    private void addBundles(Bootstrap<?> bootstrap, Injector injector) {
        Set<Class<? extends Bundle>> bundleClasses = reflections
                .getSubTypesOf(Bundle.class);
        for (Class<? extends Bundle> bundle : bundleClasses) {
            bootstrap.addBundle(injector.getInstance(bundle));
            //logger.info("Added bundle class {} during bootstrap", bundle);
        }
    }

    private void addManaged(Environment environment, Injector injector) {
        Set<Class<? extends Managed>> managedClasses = reflections
                .getSubTypesOf(Managed.class);
        for (Class<? extends Managed> managed : managedClasses) {
            environment.manage(injector.getInstance(managed));
            //logger.info("Added managed: {}", managed);
        }
    }

    private void addTasks(Environment environment, Injector injector) {
        Set<Class<? extends Task>> taskClasses = reflections
                .getSubTypesOf(Task.class);
        for (Class<? extends Task> task : taskClasses) {
            environment.addTask(injector.getInstance(task));
            //logger.info("Added task: {}", task);
        }
    }

    private void addResources(Environment environment, Injector injector) {
        Set<Class<?>> resourceClasses = reflections
                .getTypesAnnotatedWith(Path.class);
        for (Class<?> resource : resourceClasses) {
            environment.addResource(resource);
            //logger.info("Added resource class: {}", resource);
        }
    }

    private void addInjectableProviders(Environment environment, Injector injector) {
        Set<Class<? extends InjectableProvider>> injectableProviders = reflections
                .getSubTypesOf(InjectableProvider.class);
        for (Class<? extends InjectableProvider> injectableProvider : injectableProviders) {
            environment.addProvider(injectableProvider);
            //logger.info("Added injectableProvider: {}", injectableProvider);
        }
    }

    private void addProviders(Environment environment, Injector injector) {
        Set<Class<?>> providerClasses = reflections
                .getTypesAnnotatedWith(Provider.class);
        for (Class<?> provider : providerClasses) {
            environment.addProvider(provider);
            //logger.info("Added provider class: {}", provider);
        }
    }

    private void addHealthChecks(Environment environment, Injector injector) {
        Set<Class<? extends HealthCheck>> healthCheckClasses = reflections
                .getSubTypesOf(HealthCheck.class);
        for (Class<? extends HealthCheck> healthCheck : healthCheckClasses) {
            environment.addHealthCheck(injector.getInstance(healthCheck));
            // logger.info("Added healthCheck: {}", healthCheck);
        }
    }
}
