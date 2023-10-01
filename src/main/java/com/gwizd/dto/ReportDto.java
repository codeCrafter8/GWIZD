package com.gwizd.dto;

import com.gwizd.enumeration.ReportType;

public record ReportDto(
        String id,
        String comment,
        byte[] img,
        LocationCoordinatesDto locationCoordinates,
        String locationName,
        ReportType reportType
) {
}
