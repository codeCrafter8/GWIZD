package com.gwizd.controller;

import com.google.gson.Gson;
import com.gwizd.dto.ReportDto;
import com.gwizd.payload.request.CreateReportRequest;
import com.gwizd.payload.request.FilteredReportRequest;
import com.gwizd.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("api/v1/report")
@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;
    @PostMapping("animal")
    public void createReport(@RequestPart("json") String createRequest,  @RequestPart MultipartFile img) throws IOException {
        Gson gson = new Gson();
        CreateReportRequest createReportRequest = gson.fromJson(createRequest, CreateReportRequest.class);

        reportService.createReport(createReportRequest, img);
    }

    @PostMapping
    public ResponseEntity<List<ReportDto>> getAllReportsByFilters(@RequestBody FilteredReportRequest filteredReportRequest) {
        List<ReportDto> reports = reportService.getAllReportsByFilters(filteredReportRequest);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
}
