package com.wisesoft.seminar.service;


import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wisesoft.seminar.model.SeminarTopic;
import com.wisesoft.seminar.util.FormatTime;

@Service
public class GetScheduleService {
    public void getSchedule(List<SeminarTopic> morningSession, List<SeminarTopic> afternoonSession) {
        FormatTime formatTime = new FormatTime();

        // Morning Session
        LocalTime time = LocalTime.of(9, 0);
        morningSession.add(new SeminarTopic("Lunch", 240 - morningSession.stream().mapToInt(SeminarTopic::getDuration).sum()));
        for (SeminarTopic topic : morningSession) {
            topic.setTime(formatTime.formatTime(time));
            time = time.plusMinutes(topic.getDuration());
        }

        // Afternoon Session
        time = LocalTime.of(13, 0);
        afternoonSession.add(new SeminarTopic("Networking Event", 0));
        for (SeminarTopic topic : afternoonSession) {
            topic.setTime(formatTime.formatTime(time));
            time = time.plusMinutes(topic.getDuration());
        }
    }
}
