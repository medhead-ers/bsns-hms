package com.medhead.ers.bsns_hms.application.messaging.job;

import com.medhead.ers.bsns_hms.application.messaging.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Job {
    protected Event event;

    public abstract void process() throws Exception;
}
