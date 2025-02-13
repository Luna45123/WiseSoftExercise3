package com.wiseSoft.seminar.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.wiseSoft.seminar.model.ScheduleModel;
import com.wiseSoft.seminar.model.SeminarTopic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public byte[] generateScheduleReport(ScheduleModel schedule) throws Exception {
        InputStream jasperStream = new ClassPathResource("report/ag4.jrxml").getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

        List<SeminarTopic> topics = schedule.getSeminarTopic();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(topics);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("seminarTopicDataSource", dataSource);
        parameters.put("date",schedule.getDate());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}




