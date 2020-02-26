package com.app.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Earthquake {

    private String title;
    private double longitude;
    private double latitude;
    private int distance;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Earthquake that = (Earthquake) o;
        return Double.compare(that.longitude, longitude) == 0 &&
            Double.compare(that.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }
}
