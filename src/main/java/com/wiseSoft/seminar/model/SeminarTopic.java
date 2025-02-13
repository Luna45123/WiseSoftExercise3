package com.wiseSoft.seminar.model;


public class SeminarTopic {

    private final String title;
    private final int duration;
    private String time;

    public SeminarTopic(String title, int duration) {
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