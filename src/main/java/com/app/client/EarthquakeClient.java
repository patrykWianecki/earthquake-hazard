package com.app.client;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.stereotype.Component;

import com.app.model.EarthquakeResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class EarthquakeClient {

    private final static String PATH = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";

    private HttpRequest createEarthquakeRequest() throws URISyntaxException {
        return HttpRequest.newBuilder()
            .uri(new URI(EarthquakeClient.PATH))
            .version(HttpClient.Version.HTTP_2)
            .timeout(Duration.ofSeconds(10))
            .GET()
            .build();
    }

    public EarthquakeResponse getEarthquakeResponse() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = HttpClient
            .newBuilder()
            .proxy(ProxySelector.getDefault())
            .build()
            .send(createEarthquakeRequest(), HttpResponse.BodyHandlers.ofString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.fromJson(response.body(), EarthquakeResponse.class);
    }
}
