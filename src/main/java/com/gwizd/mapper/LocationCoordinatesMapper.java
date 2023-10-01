package com.gwizd.mapper;

import com.gwizd.dto.LocationCoordinatesDto;
import com.gwizd.model.LocationCoordinates;
import org.springframework.stereotype.Service;

@Service
public class LocationCoordinatesMapper {
    public LocationCoordinatesDto map(LocationCoordinates locationCoordinates) {
        return new LocationCoordinatesDto(
                locationCoordinates.getLatitude(),
                locationCoordinates.getLongitude()
        );
    }
}
