package com.wisesoft.seminar.service;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.wisesoft.seminar.model.ScheduleModel;
import com.wisesoft.seminar.model.SeminarDay;
import com.wisesoft.seminar.model.SeminarTopic;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final GenerateScheduleReportService reportService;
    private final PrintScheduleService printSchedule;
    private final SeminarSchedulerService seminarScheduler;
    private final ExtractDurationService extractDuration;

    public ReportService(GenerateScheduleReportService reportService,
            PrintScheduleService printSchedule,
            SeminarSchedulerService seminarScheduler,
            ExtractDurationService extractDuration) {
        this.reportService = reportService;
        this.printSchedule = printSchedule;
        this.seminarScheduler = seminarScheduler;
        this.extractDuration = extractDuration;
    }

    public byte[] downloadScheduleReport(List<Map<String, Object>> requestData) {
         try {
            //Extract startDate from JSON
            String startDate = (String) requestData.get(0).get("date");

            //Extract Seminar Topics from JSON
            List<SeminarTopic> topics = requestData.stream()
                    .skip(1)
                    .map(item -> {
                        String title = (String) item.get("topic");
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
            return mergedPdfBytes;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}