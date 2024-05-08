package com.brigada.bloss.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class ReceiverImpl {

    public void receive(HashMap<String, String> receivedMap) {
        log.info("Consumer received: " + receivedMap);
    }

}
