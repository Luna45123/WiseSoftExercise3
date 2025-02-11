package com.wiseSoft.seminar.seminar.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class SeminarDay {
 
    private Long id;
    
    private final LocalDate date;

    private final List<SeminarTopic> morningSession = new ArrayList<>();
    private final List<SeminarTopic> afternoonSession = new ArrayList<>();
    private String datePattern = "dd/MM/";

    public SeminarDay(LocalDate date) {
        this.date = date;
    }

    public String getFormattedDate() {
        int buddhistYear = date.getYear() + 543;
        return date.format(DateTimeFormatter.ofPattern(datePattern)) + buddhistYear;
    }

    public List<SeminarTopic> getMorningSession() {
        return morningSession;
    }

    public List<SeminarTopic> getAfternoonSession() {
        return afternoonSession;
    }

    public void addAfternoonSession(SeminarTopic seminarTopic){
        afternoonSession.add(seminarTopic);
    }

    public void addMorningSession(SeminarTopic seminarTopic){
        morningSession.add(seminarTopic);
    }

    public int getAfternoonSessionTime(){
        return afternoonSession.stream().mapToInt(SeminarTopic::getDuration).sum();
    }

    public int getMorningSessionTime(){
        return morningSession.stream().mapToInt(SeminarTopic::getDuration).sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  
}
