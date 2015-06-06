package com.hackday.knowUrHub.resources;

import com.google.inject.Inject;
import com.hackday.knowUrHub.services.ShipmentService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by abhishek.ar on 6/5/15.
 */
@Path("know_ur_hub")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipmentResource {

    ShipmentService shipmentService;

    @Inject
    public ShipmentResource(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GET
    @Path("/get_hub_specific_shipments")
    public String getHubSpecificShipments() throws IOException, ExecutionException, InterruptedException {
        return shipmentService.getHubSpecificShipments();
    }
}
