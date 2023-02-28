package com.medhead.ers.bsns_hms.data.tools;

import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    private Generator(){}

    public static List<EmergencyBedroom> emergencyBedroomsGenerator(String hospitalCode, int nbOfEmergencyBedroomsToGenerate, BedroomState bedroomState, int startIndex) {
        ArrayList<EmergencyBedroom> emergencyBedroomsList = new ArrayList<>();
        for (int i = startIndex; i < nbOfEmergencyBedroomsToGenerate + startIndex; i++) {
            emergencyBedroomsList.add(EmergencyBedroom.builder()
                    .code(hospitalCode + "_EBR_" + StringUtils.left(String.format("%03d", i), 3))
                    .state(bedroomState)
                    .build());
        }
        return emergencyBedroomsList;
    }
}
