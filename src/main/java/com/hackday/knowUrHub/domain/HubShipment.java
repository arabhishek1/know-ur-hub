package com.hackday.knowUrHub.domain;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class HubShipment {
    private Integer hubId;
    private String hubName;
    private Integer pincode;
    private Integer shipmentCount;
    private Double latitude;
    private Double longitude;

    public Integer gethubId() {
        return hubId;
    }

    public String getHubName() {
        return hubName;
    }

    public Integer getPincode() {
        return pincode;
    }

    public Integer getShipmentCount() {
        return shipmentCount;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setId(Integer id) {
        this.hubId = id;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public void setShipmentCount(Integer shipmentCount) {
        this.shipmentCount = shipmentCount;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
