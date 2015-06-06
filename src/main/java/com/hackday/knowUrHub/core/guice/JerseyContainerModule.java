package com.hackday.knowUrHub.core.guice;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.spi.container.WebApplication;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class JerseyContainerModule extends JerseyServletModule {
    private final GuiceContainer container;

    public JerseyContainerModule(final GuiceContainer container) {
        this.container = container;
    }

    @Override
    protected void configureServlets() {
        bind(GuiceContainer.class).toInstance(container);
    }

    @Override
    public WebApplication webApp(com.sun.jersey.guice.spi.container.servlet.GuiceContainer guiceContainer) {
        return container.getWebApplication();
    }
}
