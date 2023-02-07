package com.medhead.ers.bsns_hms.application.messaging.service.definition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.medhead.ers.bsns_hms.application.messaging.event.Event;

public interface MessageToEventConverter {
    Event convertMessageToEvent(String message) throws JsonProcessingException;
}
