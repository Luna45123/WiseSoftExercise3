package com.wisesoft.seminar.service;

import org.springframework.stereotype.Service;

import com.wisesoft.seminar.model.SeminarDay;

@Service
public class RemoveSessionService {
    public void remove(SeminarDay seminarDay){
        seminarDay.getMorningSession().clear();
        seminarDay.getAfternoonSession().clear();
    }
}
