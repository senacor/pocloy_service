package com.senacor.bankathon2018.webendpoint;

import com.senacor.bankathon2018.service.NotificationService;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/figo")
public class FigoEndpoint {

  private final static Logger LOG = LoggerFactory.getLogger(FigoEndpoint.class);
  private final NotificationService notificationService;

  public FigoEndpoint(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @PostMapping("/notification")
  public ResponseEntity<Void> notifyMe(@RequestBody State state) {
    LOG.info("NotifyState=" + state);
    String username = state.getState();
    boolean sendNotification = notificationService.notifyUserAboutNewTransactions(username);
    if(sendNotification) {
      LOG.info("Send a new Notification to user=" + username);
      return ResponseEntity.ok().build();
    } else {
      LOG.error("We could not send a notification to user=", username);
      return ResponseEntity.status(500).build();
    }
  }
}
