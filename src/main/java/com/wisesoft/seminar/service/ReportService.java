package com.wisesoft.seminar.service;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.wisesoft.seminar.model.Input;
import com.wisesoft.seminar.model.ScheduleModel;
import com.wisesoft.seminar.model.SeminarDay;
import com.wisesoft.seminar.model.SeminarTopic;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final GenerateScheduleReportService reportService;
    private final GetScheduleService printSchedule;
    private final SeminarSchedulerService seminarScheduler;
    private final ExtractDurationService extractDurationService;
    private static final String DURATION_PATTERN = "\\s(\\d+)min$";

    public ReportService(GenerateScheduleReportService reportService,
            GetScheduleService printSchedule,
            SeminarSchedulerService seminarScheduler,
            ExtractDurationService extractDurationService) {
        this.extractDurationService = extractDurationService;
        this.reportService = reportService;
        this.printSchedule = printSchedule;
        this.seminarScheduler = seminarScheduler;
    }

    public byte[] downloadScheduleReport(List<Input> requestData) {
        try {
            // Extract startDate from JSON
            String startDate = (String) requestData.get(0).getTopic();

            // Extract Seminar Topics from JSON
            List<SeminarTopic> topics = requestData.stream()
                    .skip(1)
                    .map(item -> {
                        String title = (String) item.getTopic();
                        Pattern PATTERN = Pattern.compile(DURATION_PATTERN);
                        Matcher matcher = PATTERN.matcher(title);
                        int duration = 0;
                        if (matcher.find()) {
                            duration = Integer.parseInt(matcher.group(1));
                        }
                        return new SeminarTopic(title.replaceAll(DURATION_PATTERN, ""), duration);
                    })
                    .collect(Collectors.toList());

            List<SeminarDay> schedules = seminarScheduler.createScheduleSeminars(startDate, topics);
            List<ScheduleModel> scheduleModels = printSchedule.getSchedule(schedules);

            // Combine multiple PDFs
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
            return mergedPdfBytes;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
