package com.hackday.knowUrHub.core.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by abhishek.ar on 6/5/15.
 */
@ToString
public class ApplicationConfiguration {
    @NotEmpty
    @JsonProperty
    private String dbReference;

    public String getDbReference() {
        return dbReference;
    }

    public void setDbReference(String dbReference) {
        this.dbReference = dbReference;
    }
}
