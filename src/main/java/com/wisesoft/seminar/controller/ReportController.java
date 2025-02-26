package com.wisesoft.seminar.controller;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisesoft.seminar.model.InputModel;
import com.wisesoft.seminar.model.SeminarTopicModel;
import com.wisesoft.seminar.service.ReportService;


@RestController
@RequestMapping("/api/seminar")
public class ReportController {
    private final ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleReport(@RequestBody List<SeminarTopicModel> requestData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=schedule_report.pdf");
            return new ResponseEntity<>(reportService.downloadScheduleReport(requestData), headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
