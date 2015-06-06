package com.hackday.knowUrHub.services.impl;

import au.com.bytecode.opencsv.CSVReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.casclient.client.HttpAuthClient;
import com.flipkart.casclient.entity.Request;
import com.google.gson.Gson;
import com.hackday.knowUrHub.domain.HubShipment;
import com.hackday.knowUrHub.services.ShipmentService;
import com.hackday.knowUrHub.utils.CSVFileReader;
import com.hackday.knowUrHub.utils.HttpClient;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.json4s.jackson.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class ShipmentServiceImpl implements ShipmentService {

    protected final HttpClient httpClient = HttpClient.INSTANCE;
    protected final ObjectMapper mapper = new ObjectMapper();
    protected final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();


    @Override
    public String getHubSpecificShipments() throws IOException, ExecutionException, InterruptedException {
        final CSVReader csvReader = CSVFileReader.readFile("/Users/abhishek.ar/Desktop/hackDaySampleSet.csv");
        List<HubShipment> hubShipmentList = new ArrayList<HubShipment>();
        //final Map<Integer, Integer> facilityShipmentCount = new HashMap<Integer, Integer>();
        String[] nextLine;
        while((nextLine = csvReader.readNext()) != null){
            Map<String,Integer> hubNamePincode = getPincodeAndHubName(Integer.parseInt(nextLine[1]));
            final HubShipment hubShipment = new HubShipment();
            hubShipment.setId(Integer.parseInt(nextLine[1]));
            hubShipment.setShipmentCount(Integer.parseInt(nextLine[0]));
            hubShipment.setPincode(hubNamePincode.values().iterator().next());
            Map<Double, Double> latLong = getLatLong(hubShipment.getPincode());
            hubShipment.setHubName(hubNamePincode.keySet().iterator().next());
            hubShipment.setLatitude(latLong.keySet().iterator().next());
            hubShipment.setLongitude(latLong.values().iterator().next());
            hubShipmentList.add(hubShipment);
        }
        Gson gson = new Gson();
        String json = gson.toJson(hubShipmentList);
        return json;
    }

    private Map<Double, Double> getLatLong(Integer pincode) throws IOException, ExecutionException, InterruptedException {
        Map<Double, Double>LatLong = new HashMap<Double, Double>();
        //final HttpAuthClient httpAuthClient = httpClient.getHttpAuthClient();
        final String mapBaseUrl = "http://maps.googleapis.com/maps/api/geocode/json";//?address="+pincode+"&sensor=false";
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headers = new HashMap<String, String>();
        queryParams.put("address",pincode.toString());
        queryParams.put("sensor","false");
        //final Request request = new Request(mapBaseUrl , null, null);
        final AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareGet(mapBaseUrl);
        for(Map.Entry<String, String> entry: queryParams.entrySet()) {
            requestBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String, String> entry: headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        final Response response = requestBuilder.execute().get();
        //final Response response = httpAuthClient.executeGet(request);
        if (response.getStatusCode() == HttpResponseStatus.OK.getCode()) {
            final JsonNode rootNode = mapper.readTree(response.getResponseBody());
            Double lat = Double.parseDouble(String.valueOf(rootNode.get("results").get(0).get("geometry").get("location").get("lat")));
            Double lng = Double.parseDouble(String.valueOf(rootNode.get("results").get(0).get("geometry").get("location").get("lng")));
            LatLong.put(lat,lng);
        }
        return LatLong;
    }

    private Map<String, Integer> getPincodeAndHubName(Integer id) throws IOException {
        Map<String, Integer>PincodeHubName = new HashMap<String, Integer>();
        final HttpAuthClient httpAuthClient = httpClient.getHttpAuthClient();
        final String facilitiesBaseUrl = "http://localhost:27747/facilities?id=";
        final Request request = new Request(facilitiesBaseUrl + id, null, null);
        final Response response = httpAuthClient.executeGet(request);
        if (response.getStatusCode() == HttpResponseStatus.OK.getCode()) {
            final JsonNode rootNode = mapper.readTree(response.getResponseBody());
            final JsonNode addressNode= rootNode.get(0).path("address");
            final Integer pincode = Integer.parseInt(addressNode.get("postal_code").textValue());
            final String hubName = rootNode.get(0).get("name").textValue();
            PincodeHubName.put(hubName, pincode);
        }
        return PincodeHubName;
    }

}
