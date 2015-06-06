package com.hackday.knowUrHub.core.configurations;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class SwaggerConfiguration {
    String apiVersion;
    String basePath;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
