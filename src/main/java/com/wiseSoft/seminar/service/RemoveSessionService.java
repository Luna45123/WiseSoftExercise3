package com.wiseSoft.seminar.service;

import org.springframework.stereotype.Service;

import com.wiseSoft.seminar.model.SeminarDay;

@Service
public class RemoveSessionService {
    public void remove(SeminarDay seminarDay){
        seminarDay.getMorningSession().clear();
        seminarDay.getAfternoonSession().clear();
    }
}
