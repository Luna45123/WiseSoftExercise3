package com.wisesoft.seminar.dto;

public class SeminarTopicDTO {
    private final String title;
    private final int duration;
    private String time;

    public SeminarTopicDTO(String title, int duration) {
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
}
