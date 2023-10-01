package com.gwizd.payload.request;

import com.gwizd.payload.Distance;

public record FilteredReportRequest(
        String reportType,
        Distance distance
) {
}
