package com.medhead.ers.bsns_hms.application.messaging.service.implementation;

import com.medhead.ers.bsns_hms.application.messaging.event.Event;
import com.medhead.ers.bsns_hms.application.messaging.job.Job;
import com.medhead.ers.bsns_hms.application.messaging.service.definition.JobMapper;
import org.springframework.stereotype.Service;

@Service
public class JobMapperImpl implements JobMapper {
    @Override
    public Job createJobFromEvent(Event event) throws Exception {
        Class<?> jobClass = Class.forName(Job.class.getPackageName() + "." + getJobNameFromEvent(event));
        return (Job) jobClass.getDeclaredConstructor(Event.class).newInstance(event);
    }

    private String getJobNameFromEvent(Event event){
        return  event.getEventType() + "Job";
    }
}
