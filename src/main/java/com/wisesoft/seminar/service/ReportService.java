package com.wisesoft.seminar.service;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.wisesoft.seminar.model.ScheduleModel;
import com.wisesoft.seminar.model.SeminarDayModel;
import com.wisesoft.seminar.model.SeminarTopicModel;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final GenerateScheduleReportService reportService;
    private final GetScheduleService printSchedule;
    private final SeminarSchedulerService seminarScheduler;

    public ReportService(GenerateScheduleReportService reportService,
            GetScheduleService printSchedule,
            SeminarSchedulerService seminarScheduler) {
        this.reportService = reportService;
        this.printSchedule = printSchedule;
        this.seminarScheduler = seminarScheduler;
    }

    public byte[] downloadScheduleReport(List<SeminarTopicModel> requestData) {
        try {
            // Extract startDate from JSON
            String startDate = (String) requestData.get(0).getTopic();

            // Extract Seminar Topics from JSON
            List<SeminarTopicModel> topics = requestData.stream()
                    .skip(1)
                    .map(item -> {
                        String topic = (String) item.getTopic();
                        int duration = (Integer) item.getDuration();
                        
                        return new SeminarTopicModel(topic, duration);
                    })
                    .collect(Collectors.toList());

            List<SeminarDayModel> schedules = seminarScheduler.createScheduleSeminars(startDate, topics);
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
