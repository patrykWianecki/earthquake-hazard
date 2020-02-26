package com.app.validator;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.app.model.Geometry;
import com.app.model.Properties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EarthquakeValidatorTest {

    @InjectMocks
    private EarthquakeValidator earthquakeValidator;

    @Test
    void should_return_true_when_coordinates_are_correct() {
        // given
        Geometry geometry = Geometry.builder().coordinates(List.of(125.213, 88.425)).build();

        // when
        boolean isGeometryValid = earthquakeValidator.validateGeometry(geometry);

        // then
        assertTrue(isGeometryValid);
    }

    @Test
    void should_return_false_when_coordinates_are_incorrect() {
        // given
        Geometry geometry = Geometry.builder().coordinates(List.of(180.1, -90.1)).build();

        // when
        boolean isGeometryValid = earthquakeValidator.validateGeometry(geometry);

        // then
        assertFalse(isGeometryValid);
    }

    @Test
    void should_return_false_when_coordinates_are_empty() {
        // given
        Geometry geometry = Geometry.builder().coordinates(List.of()).build();

        // when
        boolean isGeometryValid = earthquakeValidator.validateGeometry(geometry);

        // then
        assertFalse(isGeometryValid);
    }

    @Test
    void should_return_false_when_coordinates_are_null() {
        // given
        Geometry geometry = Geometry.builder().build();

        // when
        boolean isGeometryValid = earthquakeValidator.validateGeometry(geometry);

        // then
        assertFalse(isGeometryValid);
    }

    @Test
    void should_return_true_when_properties_are_correct() {
        // given
        Properties properties = Properties.builder().title("18km NW of Norfolk, New York").build();

        // when
        boolean isValid = earthquakeValidator.validateProperties(properties);

        // then
        assertTrue(isValid);
    }

    @Test
    void should_return_true_when_properties_are_blank() {
        // given
        Properties properties = Properties.builder().title("").build();

        // when
        boolean isValid = earthquakeValidator.validateProperties(properties);

        // then
        assertFalse(isValid);
    }

    @Test
    void should_return_true_when_properties_are_null() {
        // given
        Properties properties = Properties.builder().build();

        // when
        boolean isValid = earthquakeValidator.validateProperties(properties);

        // then
        assertFalse(isValid);
    }
}