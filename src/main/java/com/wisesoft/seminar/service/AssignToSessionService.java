package com.wisesoft.seminar.service;


import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wisesoft.seminar.model.SeminarTopicModel;

@Service
public class AssignToSessionService {
    public void assign(List<SeminarTopicModel> session, List<SeminarTopicModel> topics, int maxDuration ,String breakText) {
        int currentDuration = session.stream().mapToInt(SeminarTopicModel::getDuration).sum();
        Iterator<SeminarTopicModel> iterator = topics.iterator();

        while (iterator.hasNext()) {
            SeminarTopicModel topic = iterator.next();
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
