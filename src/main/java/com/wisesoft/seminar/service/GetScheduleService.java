package com.wisesoft.seminar.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wisesoft.seminar.model.ScheduleModel;
import com.wisesoft.seminar.model.SeminarDay;
import com.wisesoft.seminar.model.SeminarTopic;
import com.wisesoft.seminar.util.FormatTime;

@Service
public class GetScheduleService {
    public List<ScheduleModel> getSchedule(List<SeminarDay> schedule) {
        String dayText = "Day ";
        String lunchText = "Lunch";
        String networkingEventText = "Networking Event";
        String dateSpace = " : ";
        List<ScheduleModel> scheduleModels = new ArrayList<>();

        for (int i = 0; i < schedule.size(); i++) {
            List<SeminarTopic> allTopics = new ArrayList<>();
            ScheduleModel scheduleModel = new ScheduleModel();
            SeminarDay day = schedule.get(i);
            LocalTime time = LocalTime.of(9, 0);
            List<SeminarTopic> morningSession = new ArrayList<>(day.getMorningSession());
            morningSession.add(
                    new SeminarTopic(lunchText, 240 - morningSession.stream().mapToInt(SeminarTopic::getDuration).sum()));

            for (SeminarTopic topic : morningSession) {
                topic.setTime(FormatTime.formatTime(time));
                time = time.plusMinutes(topic.getDuration());
            }

            time = LocalTime.of(13, 0);
            List<SeminarTopic> afternoonSession = new ArrayList<>(day.getAfternoonSession());
            afternoonSession.add(new SeminarTopic(networkingEventText, 0));

            for (SeminarTopic topic : afternoonSession) {
                topic.setTime(FormatTime.formatTime(time));
                time = time.plusMinutes(topic.getDuration());
            }
            allTopics.addAll(morningSession);
            allTopics.addAll(afternoonSession);
            scheduleModel.setSeminarTopic(allTopics);
            scheduleModel.setDate(dayText + (i + 1) + dateSpace + day.getFormattedDate());

            scheduleModels.add(scheduleModel);
        }

        return scheduleModels;
    }
}
