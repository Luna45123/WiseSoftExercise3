package com.wiseSoft.seminar.seminar.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.wiseSoft.seminar.seminar.model.ScheduleModel;
import com.wiseSoft.seminar.seminar.model.SeminarDay;
import com.wiseSoft.seminar.seminar.model.SeminarTopic;
import com.wiseSoft.seminar.seminar.service.ExtractDurationService;
import com.wiseSoft.seminar.seminar.service.PrintScheduleService;
import com.wiseSoft.seminar.seminar.service.ReportService;
import com.wiseSoft.seminar.seminar.service.SeminarSchedulerService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final PrintScheduleService printSchedule;
    private final SeminarSchedulerService seminarScheduler;
    private final ExtractDurationService extractDuration;

    public ReportController(ReportService reportService,
            PrintScheduleService printSchedule,
            SeminarSchedulerService seminarScheduler,
            ExtractDurationService extractDuration) {
        this.reportService = reportService;
        this.printSchedule = printSchedule;
        this.seminarScheduler = seminarScheduler;
        this.extractDuration = extractDuration;
    }

    @PostMapping("/schedule")
    public ResponseEntity<byte[]> downloadScheduleReport(@RequestBody List<Map<String, Object>> requestData) {
        try {
            //Extract startDate from JSON
            String startDate = (String) requestData.get(0).get("date");

            //Extract Seminar Topics from JSON
            List<SeminarTopic> topics = requestData.stream()
                    .skip(1)
                    .map(item -> {
                        String title = (String) item.get("title");
                        int duration = extractDuration.extractDuration(title);
                        return new SeminarTopic(title.replaceAll("\\s*\\d+min$", ""), duration);
                    })
                    .collect(Collectors.toList());

            List<SeminarDay> schedules = seminarScheduler.createScheduleSeminars(startDate, topics);
            List<ScheduleModel> scheduleModels = printSchedule.print(schedules);

            //Combine multiple PDFs
            ByteArrayOutputStream mergedPdfOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, mergedPdfOutputStream);
            document.open();

            for (ScheduleModel schedule : scheduleModels) {
                byte[] pdfReport = reportService.generateScheduleReport(schedule);
                PdfReader reader = new PdfReader(pdfReport);
                int n = reader.getNumberOfPages();
                for (int i = 1; i <= n; i++) {
                    copy.addPage(copy.getImportedPage(reader, i));
                }
                reader.close();
            }

            document.close();
            byte[] mergedPdfBytes = mergedPdfOutputStream.toByteArray();

            //Return merged PDF
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=schedule_report.pdf");
            return new ResponseEntity<>(mergedPdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
