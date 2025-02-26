package com.wisesoft.seminar.dto;


public class ScheduleDTO {

    private final String topic;
    private final int duration;
    private String time;

    public ScheduleDTO(String title, int duration) {
        this.topic = title;
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
}
