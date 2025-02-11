package com.wiseSoft.seminar.seminar.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wiseSoft.seminar.seminar.model.ScheduleModel;
import com.wiseSoft.seminar.seminar.model.SeminarDay;
import com.wiseSoft.seminar.seminar.model.SeminarTopic;

@Service
public class PrintScheduleService {

    private final GetScheduleService getSchedule;

    public PrintScheduleService(GetScheduleService getSchedule) {
        this.getSchedule = getSchedule;
    }



    public List<ScheduleModel> print(List<SeminarDay> schedule){
        String dayText = "Day ";
        List<ScheduleModel> ScheduleModels = new ArrayList<>();
        for (int i = 0; i < schedule.size(); i++) {
            List<SeminarTopic> all = new ArrayList<>();
            ScheduleModel ScheduleModel = new ScheduleModel();
            SeminarDay day = schedule.get(i);
            getSchedule.getSchedule(day.getMorningSession(),day.getAfternoonSession());
            all.addAll(schedule.get(i).getMorningSession());
            all.addAll(schedule.get(i).getAfternoonSession());
            ScheduleModel.setSeminarTopic(all);
            ScheduleModel.setDate(dayText + (i+1) + " : " +schedule.get(i).getFormattedDate());
            ScheduleModels.add(ScheduleModel);
        }
        
        return ScheduleModels;
    }
}
