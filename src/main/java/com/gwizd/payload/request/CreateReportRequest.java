package com.gwizd.payload.request;

import com.gwizd.model.LocationCoordinates;

public record CreateReportRequest(
        String comment,
        byte[] img,
        LocationCoordinates locationCoordinates,
        String locationName,
        String reportType
) {
}
