package com.gwizd.service;

import com.gwizd.dto.LocationCoordinatesDto;
import com.gwizd.dto.ReportDto;
import com.gwizd.enumeration.ReportType;
import com.gwizd.mapper.LocationCoordinatesMapper;
import com.gwizd.mapper.ReportMapper;
import com.gwizd.model.Report;
import com.gwizd.payload.request.CreateReportRequest;
import com.gwizd.payload.request.FilteredReportRequest;
import com.gwizd.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final LocationCoordinatesMapper locationCoordinatesMapper;
    private final FirebaseMessageService firebaseMessageService;

    public void createReport(CreateReportRequest createRequest, MultipartFile img) throws IOException {
        Map<String, String> details = new HashMap<>();
        details.put("comment", createRequest.comment());
        details.put("latitude", String.valueOf(createRequest.locationCoordinates().getLatitude()));
        details.put("longitude", String.valueOf(createRequest.locationCoordinates().getLongitude()));
        details.put("reportType", createRequest.reportType());

        firebaseMessageService.sendMessage(details);

        Report report = null;
        try {
            report = reportMapper.map(createRequest, img.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        reportRepository.save(report);
    }

    public List<ReportDto> getAllReportsByFilters(FilteredReportRequest filteredReportRequest) {
        List<ReportDto> result = new ArrayList<>();

        List<Report> allReports = reportRepository.findAll();

        for(Report report : allReports) {
            if(doesMeetAssumptions(report, filteredReportRequest)) {
                LocationCoordinatesDto locationCoordinatesDto = locationCoordinatesMapper.map(report.getLocationCoordinates());
                ReportDto reportDto = reportMapper.map(report, locationCoordinatesDto);
                result.add(reportDto);
            }
        }

        return result;
    }

    public boolean doesMeetAssumptions(Report report, FilteredReportRequest request) {
        if (request.reportType() != null
                && !ReportType.valueOf(request.reportType()).equals(report.getReportType())) {
            return false;
        }

        if (request.distance() != null
                && request.distance().getDistance() != null
                && request.distance().getUserLocationCoordinates() != null) {
            double latitude1 = request.distance().getUserLocationCoordinates().getLatitude();
            double longitude1 = request.distance().getUserLocationCoordinates().getLongitude();
            double latitude2 = report.getLocationCoordinates().getLatitude();
            double longitude2 = report.getLocationCoordinates().getLongitude();
            double distance = calculateDistance(latitude1, longitude1, latitude2, longitude2);
            if (distance > request.distance().getDistance()) {
                return false;
            }
        }

        return true;
    }

    private double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double R = 6371000;

        double lat1 = Math.toRadians(latitude1);
        double lon1 = Math.toRadians(longitude1);
        double lat2 = Math.toRadians(latitude2);
        double lon2 = Math.toRadians(longitude2);

        double dLon = lon2 - lon1;
        double dLat = lat2 - lat1;

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
