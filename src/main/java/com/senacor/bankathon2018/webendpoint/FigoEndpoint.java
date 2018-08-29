package com.senacor.bankathon2018.webendpoint;

import com.senacor.bankathon2018.webendpoint.model.requestDTO.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/figo")
public class FigoEndpoint {
    private final static Logger LOG = LoggerFactory.getLogger(FigoEndpoint.class);

    @PostMapping("/notification")
    public ResponseEntity<Void> notifyMe(@RequestBody State state) {
        LOG.info("NotifyState="+state);
        return ResponseEntity.ok().build();
    }
}
