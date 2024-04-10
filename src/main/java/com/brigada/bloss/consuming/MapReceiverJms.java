package com.brigada.bloss.consuming;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.brigada.bloss.service.NetflixFilmService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MapReceiverJms {

    @Autowired
    private NetflixFilmService netflixFilmService;
    
    public void receive(HashMap<String, String> receivedMap) {
        log.info("received " + receivedMap);
        netflixFilmService.processMappedData(receivedMap);
    }

}
