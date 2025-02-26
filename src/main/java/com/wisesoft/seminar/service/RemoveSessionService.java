package com.wisesoft.seminar.service;

import org.springframework.stereotype.Service;

import com.wisesoft.seminar.model.SeminarDayModel;

@Service
public class RemoveSessionService {
    public void remove(SeminarDayModel seminarDay){
        seminarDay.getMorningSession().clear();
        seminarDay.getAfternoonSession().clear();
    }
}
