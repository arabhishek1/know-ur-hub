package com.hackday.knowUrHub.utils;

import com.flipkart.casclient.client.HttpAuthClient;
import com.flipkart.casclient.util.InMemoryCache;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * Created by abhishek.ar on 6/5/15.
 */
public enum  HttpClient {
    INSTANCE;

    private final AsyncHttpClient asyncHttpClient;
    private final HttpAuthClient httpAuthClient;

    private HttpClient() {
        asyncHttpClient = new AsyncHttpClient();
        httpAuthClient = initHttpAuthClient();
    }

    private HttpAuthClient initHttpAuthClient() {
        final String casUrl = "https://flo-rubycas-server.nm.flipkart.com";
        final String casUser = "fk-ekl-gringotts";
        final String casPassword = "4BDRnuMS";
        return new HttpAuthClient(casUrl, casUser, casPassword, true, new InMemoryCache());
    }

    public Response executeGet(String url) throws InterruptedException, ExecutionException, IOException {
        final Map<String, String> headers = new HashMap<String, String>();
        final Map<String, String> params = new HashMap<String, String>();
        return executeGet(url, params, headers);
    }

    public Response executeGet(String url, Map<String, String> queryParams, Map<String, String> headers)
            throws InterruptedException, ExecutionException, IOException {
        final BoundRequestBuilder requestBuilder = asyncHttpClient.prepareGet(url);
        for(Map.Entry<String, String> entry: queryParams.entrySet()) {
            requestBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String, String> entry: headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        return requestBuilder.execute().get();
    }


    /**
     *
     * @param url
     * @param body
     * @param headers
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public Response executePost(String url, String body, Map<String, String> headers)
            throws InterruptedException, ExecutionException, IOException {

        final FluentCaseInsensitiveStringsMap map = new FluentCaseInsensitiveStringsMap();
        for(Map.Entry<String, String> entry: headers.entrySet()) {
            map.add(entry.getKey(), entry.getValue());
        }

        return asyncHttpClient.preparePost(url).setBody(body).setHeaders(map).execute().get();
    }

    /**
     * @return
     */
    public HttpAuthClient getHttpAuthClient() {
        return httpAuthClient;
    }


}
