package com.hackday.knowUrHub.services;

import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public interface ShipmentService {

    public String getHubSpecificShipments() throws IOException, ExecutionException, InterruptedException;

}
