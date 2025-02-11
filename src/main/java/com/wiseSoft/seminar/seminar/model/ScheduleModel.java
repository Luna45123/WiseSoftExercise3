package com.wiseSoft.seminar.seminar.model;

import java.util.List;

public class ScheduleModel {
    private String date;
    List<SeminarTopic> seminarTopic;

    public List<SeminarTopic> getSeminarTopic() {
        return seminarTopic;
    }

    public void setSeminarTopic(List<SeminarTopic> seminarTopic) {
        this.seminarTopic = seminarTopic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
