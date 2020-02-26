package com.app.validator;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.app.model.Geometry;
import com.app.model.Properties;

import static org.apache.commons.lang3.StringUtils.*;

@Component
public class EarthquakeValidator {

    public boolean validateGeometry(final Geometry geometry) {
        return geometry != null && validateCoordinates(geometry.getCoordinates());
    }

    public boolean validateProperties(final Properties properties) {
        return !isBlank(properties.getTitle());
    }

    private static boolean validateCoordinates(final List<Double> coordinates) {
        return CollectionUtils.isNotEmpty(coordinates) &&
            validateLongitude(Optional.ofNullable(coordinates.get(0)).orElse(181.0)) &&
            validateLatitude(Optional.ofNullable(coordinates.get(1)).orElse(-91.0));
    }

    private static boolean validateLongitude(final Double longitude) {
        return longitude != null && longitude <= 180 && longitude >= -180;
    }

    private static boolean validateLatitude(final Double latitude) {
        return latitude != null && latitude <= 90 && latitude >= -90;
    }
}
