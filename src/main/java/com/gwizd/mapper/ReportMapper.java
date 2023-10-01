package com.gwizd.mapper;

import com.gwizd.dto.LocationCoordinatesDto;
import com.gwizd.dto.ReportDto;
import com.gwizd.enumeration.ReportType;
import com.gwizd.model.Report;
import com.gwizd.payload.request.CreateReportRequest;
import org.springframework.stereotype.Service;

@Service
public class ReportMapper {
    public Report map(CreateReportRequest createRequest, byte[] img) {
        return new Report(
                createRequest.comment(),
                createRequest.img(),
                createRequest.locationCoordinates(),
                createRequest.locationName(),
                ReportType.valueOf(createRequest.reportType())
        );
    }

    public ReportDto map(Report report, LocationCoordinatesDto locationCoordinatesDto) {
        return new ReportDto(
                String.valueOf(report.getId()),
                report.getComment(),
                report.getImg(),
                locationCoordinatesDto,
                report.getLocationName(),
                report.getReportType()
        );
    }
}
