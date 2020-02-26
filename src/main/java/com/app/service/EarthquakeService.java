package com.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.client.EarthquakeClient;
import com.app.model.Earthquake;
import com.app.model.EarthquakeResponse;
import com.app.model.Feature;
import com.app.validator.EarthquakeValidator;

import lombok.RequiredArgsConstructor;

import static java.lang.Math.*;
import static org.apache.commons.collections4.CollectionUtils.*;

@Service
@RequiredArgsConstructor
public class EarthquakeService {

    private final EarthquakeClient earthquakeClient;
    private final EarthquakeValidator earthquakeValidator;

    private final static double EARTH_RADIUS = 6371;

    public String createResultMessage(final double longitude, final double latitude) throws InterruptedException, IOException,
        URISyntaxException {
        return findTenClosestEarthquakes(longitude, latitude)
            .stream()
            .map(earthquake -> earthquake.getTitle() + " || " + earthquake.getDistance())
            .collect(Collectors.joining("\n"));
    }

    private List<Earthquake> findTenClosestEarthquakes(final double longitude, final double latitude) throws InterruptedException,
        IOException, URISyntaxException {
        return collectAllEarthquakes(longitude, latitude)
            .stream()
            .sorted(Comparator.comparing(Earthquake::getDistance))
            .limit(10)
            .collect(Collectors.toList());
    }

    private Set<Earthquake> collectAllEarthquakes(final double longitude, final double latitude) throws InterruptedException, IOException,
        URISyntaxException {
        final EarthquakeResponse response = earthquakeClient.getEarthquakeResponse();
        if (response == null || !isNotEmpty(response.getFeatures())) {
            throw new IllegalArgumentException("Missing at least one feature");
        }

        return response.getFeatures()
            .stream()
            .filter(feature -> earthquakeValidator.validateGeometry(feature.getGeometry()) &&
                earthquakeValidator.validateProperties(feature.getProperties()))
            .map(feature -> createEarthquake(feature, longitude, latitude))
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(HashSet::new));
    }

    private static Earthquake createEarthquake(final Feature feature, final double longitude, final double latitude) {
        return Earthquake.builder()
            .title(feature.getProperties().getTitle())
            .longitude(feature.getGeometry().getCoordinates().get(0))
            .latitude(feature.getGeometry().getCoordinates().get(1))
            .distance(calculateDistance(feature, longitude, latitude))
            .build();
    }

    private static int calculateDistance(final Feature feature, double longitude, double latitude) {
        double earthquakeLongitude = feature.getGeometry().getCoordinates().get(0);
        double earthquakeLatitude = feature.getGeometry().getCoordinates().get(1);

        if (earthquakeLatitude == latitude && earthquakeLongitude == longitude) {
            return 0;
        }

        double distanceBetweenLongitudes = toRadians(longitude - earthquakeLongitude);
        double distanceBetweenLatitudes = toRadians(latitude - earthquakeLatitude);

        latitude = toRadians(latitude);
        earthquakeLatitude = toRadians(earthquakeLatitude);

        BigDecimal distance = BigDecimal.valueOf(2 * asin(
            sqrt(
                pow(sin(distanceBetweenLatitudes / 2), 2) +
                    pow(sin(distanceBetweenLongitudes / 2), 2) *
                        cos(latitude) *
                        cos(earthquakeLatitude)
            )
        ));

        return distance.multiply(BigDecimal.valueOf(EARTH_RADIUS)).setScale(0, RoundingMode.HALF_UP).intValue();
    }
}
