package com.wisesoft.seminar.service;


import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wisesoft.seminar.model.SeminarTopic;

@Service
public class AssignToSessionService {
    public void assign(List<SeminarTopic> session, List<SeminarTopic> topics, int maxDuration ,String breakText) {
        int currentDuration = session.stream().mapToInt(SeminarTopic::getDuration).sum();
        Iterator<SeminarTopic> iterator = topics.iterator();

        while (iterator.hasNext()) {
            SeminarTopic topic = iterator.next();
            if (currentDuration + topic.getDuration() <= maxDuration) {
                session.add(topic);
                currentDuration += topic.getDuration();
                iterator.remove();
                if (currentDuration >= maxDuration){
                    break;
                }
            }
        }
    }
}
