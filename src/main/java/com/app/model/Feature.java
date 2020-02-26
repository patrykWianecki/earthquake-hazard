package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Feature {

    private Properties properties;
    private Geometry geometry;
}
