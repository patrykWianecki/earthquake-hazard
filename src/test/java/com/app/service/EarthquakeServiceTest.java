package com.app.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.app.client.EarthquakeClient;
import com.app.model.Geometry;
import com.app.model.Properties;
import com.app.validator.EarthquakeValidator;

import static com.app.data.MockDataForTests.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EarthquakeServiceTest {

    private final static double LONGITUDE = 0.0;
    private final static double LATITUDE = 0.0;

    @Mock
    private EarthquakeClient earthquakeClient;
    @Mock
    private EarthquakeValidator earthquakeValidator;

    @InjectMocks
    private EarthquakeService earthquakeService;

    @Test
    void should_contain_proper_output() throws InterruptedException, IOException, URISyntaxException {
        // given
        when(earthquakeClient.getEarthquakeResponse()).thenReturn(createCorrectEarthquakeResponse());
        when(earthquakeValidator.validateGeometry(any(Geometry.class))).thenReturn(true);
        when(earthquakeValidator.validateProperties(any(Properties.class))).thenReturn(true);

        // when
        String output = earthquakeService.createResultMessage(LONGITUDE, LATITUDE);
        String[] numberOfEarthquakes = output.split("\n");

        // then
        assertNotNull(output);
        assertFalse(output.isBlank());
        assertTrue(output.contains(FIRST_LOCATION));
        assertTrue(output.contains(SECOND_LOCATION));
        assertEquals(2, numberOfEarthquakes.length);
    }

    @Test
    void should_contains_distinct_location() throws InterruptedException, IOException, URISyntaxException {
        // given
        when(earthquakeClient.getEarthquakeResponse()).thenReturn(createResponseWithDuplicateLocation());
        when(earthquakeValidator.validateGeometry(any(Geometry.class))).thenReturn(true);
        when(earthquakeValidator.validateProperties(any(Properties.class))).thenReturn(true);

        // when
        String output = earthquakeService.createResultMessage(LONGITUDE, LATITUDE);
        String[] numberOfEarthquakes = output.split("\n");

        // then
        assertEquals(1, numberOfEarthquakes.length);
    }

    @Test
    void should_throw_exception_when_response_is_empty() throws InterruptedException, IOException, URISyntaxException {
        // given
        when(earthquakeClient.getEarthquakeResponse()).thenReturn(createEmptyResponse());

        // when + then
        assertThrows(IllegalArgumentException.class, () -> earthquakeService.createResultMessage(LONGITUDE, LATITUDE));
    }
}