package com.hackday.knowUrHub.core.configurations;

import lombok.ToString;

import java.util.Map;

/**
 * Created by abhishek.ar on 6/5/15.
 */
@ToString
public class MysqlConfiguration {
    Map<String, String> know_ur_hub;

    public Map<String, String> getKnow_ur_hub() {
        return know_ur_hub;
    }

    public void setKnow_ur_hub(Map<String, String> know_ur_hub) {
        this.know_ur_hub = know_ur_hub;
    }
}
