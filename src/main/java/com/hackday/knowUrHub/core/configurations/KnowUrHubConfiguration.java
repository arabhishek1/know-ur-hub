package com.hackday.knowUrHub.core.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;

/**
 * Created by abhishek.ar on 6/5/15.
 */
@ToString
public class KnowUrHubConfiguration extends Configuration {

    @Valid
    @JsonProperty
    private String hostName;

    @Valid
    @JsonProperty
    private MysqlConfiguration mysql;

    @Valid
    @JsonProperty
    private ApplicationConfiguration applicationConfiguration;

    @Valid
    @JsonProperty
    private SwaggerConfiguration swagger;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public MysqlConfiguration getMysql() {
        return mysql;
    }

    public void setMysql(MysqlConfiguration mysqlConfiguration) {
        this.mysql = mysqlConfiguration;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    public SwaggerConfiguration getSwagger() {
        return swagger;
    }

    public void setSwagger(SwaggerConfiguration swagger) {
        this.swagger = swagger;
    }
}
