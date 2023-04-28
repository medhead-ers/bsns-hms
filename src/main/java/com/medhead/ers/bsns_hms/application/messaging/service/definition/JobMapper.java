package com.medhead.ers.bsns_hms.application.messaging.service.definition;

import com.medhead.ers.bsns_hms.application.messaging.event.Event;
import com.medhead.ers.bsns_hms.application.messaging.job.Job;

public interface JobMapper {
    Job createJobFromEvent(Event event) throws Exception;
    boolean checkIfJobExistForEvent(Event event);
}
