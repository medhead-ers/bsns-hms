package com.medhead.ers.bsns_hms.units;

import com.medhead.ers.bsns_hms.application.messaging.exception.CannotCreateEventFromJSONMessageException;
import com.medhead.ers.bsns_hms.application.messaging.redis.config.MessageListener;
import com.medhead.ers.bsns_hms.application.messaging.service.implementation.JobMapperImpl;
import com.medhead.ers.bsns_hms.application.messaging.service.implementation.RedisMessageToEventConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static java.util.UUID.randomUUID;
@ExtendWith(OutputCaptureExtension.class)
public class MessageListenerTest {

    @Test
    void test_CannotProcessSayHelloJobFromBecauseInvalidJsonMessage(CapturedOutput output){
        // Given
        MessageListener messageListener = new MessageListener(new JobMapperImpl(), new RedisMessageToEventConverter());
        String userName = String.valueOf(randomUUID());
        String invalidMessage = "{\r\n  \"eventType\" : \"InvalidMessage\",\r\n  \"metadata\" : {\r\n    \"userName\" : \""+userName+"\"\r\n  }\r\n}";
        // Then
        Assertions.assertThrows(CannotCreateEventFromJSONMessageException.class, () -> {
            // When
            messageListener.receiveMessage(invalidMessage);
        });
    }
}
