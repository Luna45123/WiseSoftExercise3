package com.wisesoft.seminar.service;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wisesoft.seminar.model.ScheduleModel;
import com.wisesoft.seminar.model.SeminarDayModel;
import com.wisesoft.seminar.model.SeminarTopicModel;

@Service
public class SeminarService {

    private final SeminarSchedulerService seminarScheduler;
    private final GetScheduleService printSchedule;
    private final ParseSeminarTopicsService parseSeminarTopics;
    private final ReaderFileService reader;

    public SeminarService(SeminarSchedulerService seminarScheduler, GetScheduleService printSchedule,
            ParseSeminarTopicsService parseSeminarTopics, ReaderFileService reader) {
        this.seminarScheduler = seminarScheduler;
        this.printSchedule = printSchedule;
        this.parseSeminarTopics = parseSeminarTopics;
        this.reader = reader;
    }
    
    public List<ScheduleModel> scheduleSeminar(MultipartFile file) throws IOException {
        try {
            String startDate = reader.read(file).getFirst();
            List<SeminarTopicModel> topics = parseSeminarTopics.parseSeminarTopics(reader.read(file));
            List<SeminarDayModel> schedule = seminarScheduler.createScheduleSeminars(startDate, topics);
            List<ScheduleModel> scheduleModels = printSchedule.getSchedule(schedule);
            return scheduleModels;
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }
}
