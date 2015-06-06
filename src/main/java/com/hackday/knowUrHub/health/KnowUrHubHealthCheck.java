package com.hackday.knowUrHub.health;

import com.google.inject.Inject;
import com.yammer.metrics.core.HealthCheck;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class KnowUrHubHealthCheck extends HealthCheck {

    @Inject
    public KnowUrHubHealthCheck(String name) {
        super(name);
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        return Result.healthy("Application is running!");
    }
}
