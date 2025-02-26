package com.wisesoft.seminar.model;

import java.util.List;

public class ScheduleModel {
    private String date;
    List<SeminarTopicModel> seminarTopic;

    public List<SeminarTopicModel> getSeminarTopic() {
        return seminarTopic;
    }

    public void setSeminarTopic(List<SeminarTopicModel> seminarTopic) {
        this.seminarTopic = seminarTopic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
