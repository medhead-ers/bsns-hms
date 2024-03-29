package com.medhead.ers.bsns_hms.application.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    protected AvailableEvent eventType;
    protected LinkedHashMap<String, Object> metadata;
}
