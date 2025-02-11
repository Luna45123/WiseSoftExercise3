package com.wiseSoft.seminar.seminar.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.wiseSoft.seminar.seminar.model.SeminarDay;
import com.wiseSoft.seminar.seminar.model.SeminarTopic;
import com.wiseSoft.seminar.seminar.util.IncrementDate;

@Service
public class SeminarSchedulerService {

    private final AssignToSessionService assignToSession;

    private final RemoveSessionService removeSession;

    

    public SeminarSchedulerService(AssignToSessionService assignToSession, RemoveSessionService removeSession) {
        this.assignToSession = assignToSession;
        this.removeSession = removeSession;
    }



    public List<SeminarDay> createScheduleSeminars(String startDate, List<SeminarTopic> topics) {
        List<SeminarDay> days = new ArrayList<>();
        List<SeminarDay> overDay = new ArrayList<>();
        IncrementDate incrementDate = new IncrementDate();
        LocalDate date = LocalDate.parse(startDate);
        int morningMaxTime = 180;
        int afternoonMaxTime = 180;
        int maxDayTime = 420;

        while (!topics.isEmpty()) {
            SeminarDay day = new SeminarDay(date);
            assignToSession.assign(day.getMorningSession(), topics, morningMaxTime,"Lunch");
            assignToSession.assign(day.getAfternoonSession(), topics, afternoonMaxTime,"Networking Event");
            days.add(day);
            date = incrementDate.incrementDate(date);
        }
        // Add over day if session time < 4.00pm
        if ((days.getLast().getAfternoonSessionTime() + days.getLast().getMorningSessionTime()) < (morningMaxTime + afternoonMaxTime)) {
            if (!days.getLast().getMorningSession().isEmpty() || !days.getLast().getAfternoonSession().isEmpty()){
                overDay.add(days.getLast());
            }
        }


        if (!overDay.isEmpty()) {
            List<SeminarTopic> allSessions = new ArrayList<>();
            allSessions.addAll(overDay.getFirst().getMorningSession());
            allSessions.addAll(overDay.getFirst().getAfternoonSession());
            removeSession.remove(overDay.getFirst());

            for (SeminarDay seminarDay : days) {
                int morningSessionTime = seminarDay.getMorningSessionTime();
                int afternoonSessionTime = seminarDay.getAfternoonSessionTime();
                int allSessionsSize = allSessions.size();
                for (int j = 0; j < allSessionsSize; j++) {
                    if (morningSessionTime + allSessions.getFirst().getDuration() <= morningMaxTime && !allSessions.isEmpty()) {
                        seminarDay.addMorningSession(allSessions.removeFirst());
                        morningSessionTime = seminarDay.getMorningSessionTime();
                    } else if (afternoonSessionTime + allSessions.getFirst().getDuration() <= (maxDayTime - afternoonMaxTime) && !allSessions.isEmpty()) {
                        seminarDay.addAfternoonSession(allSessions.removeFirst());
                        afternoonSessionTime = seminarDay.getAfternoonSessionTime();
                    }
                    //break if day full
                    if (morningSessionTime + afternoonSessionTime >= maxDayTime){
                        break;
                    }
                }
            }

        }
        days.removeIf(day -> day.getMorningSession().isEmpty() && day.getAfternoonSession().isEmpty());
        return days;
    }
}