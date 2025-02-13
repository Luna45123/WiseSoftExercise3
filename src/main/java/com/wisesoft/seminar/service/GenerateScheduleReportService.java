package com.wisesoft.seminar.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.wisesoft.seminar.model.ScheduleModel;
import com.wisesoft.seminar.model.SeminarTopic;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class GenerateScheduleReportService {
    public byte[] generateScheduleReport(ScheduleModel schedule) throws Exception {
        InputStream jasperStream = new ClassPathResource("report/ag4.jrxml").getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

        List<SeminarTopic> topics = schedule.getSeminarTopic();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(topics);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("seminarTopicDataSource", dataSource);
        parameters.put("date", schedule.getDate());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
