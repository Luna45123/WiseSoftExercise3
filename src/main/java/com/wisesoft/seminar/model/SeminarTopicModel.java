package com.wisesoft.seminar.model;


public class SeminarTopicModel {

    private final String topic;
    private final int duration;
    private String time;

    public SeminarTopicModel(String topic, int duration) {
        this.topic = topic;
        this.duration = duration;
    }

    public String getTopic() {
        return topic;
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