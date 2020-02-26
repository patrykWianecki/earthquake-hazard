package com.app.data;

import java.util.List;

import com.app.model.EarthquakeResponse;
import com.app.model.Feature;
import com.app.model.Geometry;
import com.app.model.Properties;

public class MockDataForTests {

    public final static String FIRST_LOCATION = "Southwest of Africa";
    public final static String SECOND_LOCATION = "Southern Mid-Atlantic Ridge";

    public static EarthquakeResponse createResponseWithDuplicateLocation() {
        return EarthquakeResponse.builder()
            .features(List.of(
                Feature.builder()
                    .geometry(Geometry.builder()
                        .coordinates(List.of(10.0, 10.0))
                        .build())
                    .properties(Properties.builder()
                        .title(FIRST_LOCATION)
                        .build())
                    .build(),
                Feature.builder()
                    .geometry(Geometry.builder()
                        .coordinates(List.of(10.0, 10.0))
                        .build())
                    .properties(Properties.builder()
                        .title(SECOND_LOCATION)
                        .build())
                    .build()
            ))
            .build();
    }

    public static EarthquakeResponse createCorrectEarthquakeResponse() {
        return EarthquakeResponse.builder()
            .features(List.of(
                Feature.builder()
                    .geometry(Geometry.builder()
                        .coordinates(List.of(-10.0, 10.0))
                        .build())
                    .properties(Properties.builder()
                        .title(FIRST_LOCATION)
                        .build())
                    .build(),
                Feature.builder()
                    .geometry(Geometry.builder()
                        .coordinates(List.of(10.0, -10.0))
                        .build())
                    .properties(Properties.builder()
                        .title(SECOND_LOCATION)
                        .build())
                    .build()
            ))
            .build();
    }

    public static EarthquakeResponse createEmptyResponse() {
        return EarthquakeResponse.builder().build();
    }
}
