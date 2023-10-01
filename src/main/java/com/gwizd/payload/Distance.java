package com.gwizd.payload;

import com.gwizd.model.LocationCoordinates;
import lombok.Getter;

@Getter
public class Distance {
    private LocationCoordinates userLocationCoordinates;
    private Long distance;
}
