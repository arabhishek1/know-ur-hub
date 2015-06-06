package com.hackday.knowUrHub.modules;

import com.google.inject.AbstractModule;
import com.hackday.knowUrHub.services.ShipmentService;
import com.hackday.knowUrHub.services.impl.ShipmentServiceImpl;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class KnowUrHubClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ShipmentService.class).to(ShipmentServiceImpl.class);
    }
}
