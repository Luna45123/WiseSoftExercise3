package com.wisesoft.seminar.model;


public class SeminarTopicModel {

    private final String title;
    private final int duration;
    private String time;

    public SeminarTopicModel(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}