package com.wisesoft.seminar.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SeminarDayModel {
    private final LocalDate date;

    private final List<SeminarTopicModel> morningSession = new ArrayList<>();
    private final List<SeminarTopicModel> afternoonSession = new ArrayList<>();
    private String datePattern = "dd/MM/";

    public SeminarDayModel(LocalDate date) {
        this.date = date;
    }

    public String getFormattedDate() {
        int buddhistYear = date.getYear() + 543;
        return date.format(DateTimeFormatter.ofPattern(datePattern)) + buddhistYear;
    }

    public List<SeminarTopicModel> getMorningSession() {
        return morningSession;
    }

    public List<SeminarTopicModel> getAfternoonSession() {
        return afternoonSession;
    }

    public void addAfternoonSession(SeminarTopicModel seminarTopic){
        afternoonSession.add(seminarTopic);
    }

    public void addMorningSession(SeminarTopicModel seminarTopic){
        morningSession.add(seminarTopic);
    }

    public int getAfternoonSessionTime(){
        return afternoonSession.stream().mapToInt(SeminarTopicModel::getDuration).sum();
    }

    public int getMorningSessionTime(){
        return morningSession.stream().mapToInt(SeminarTopicModel::getDuration).sum();
    }
}
