package com.senacor.bankathon2018.connectors.model.figo;

import java.util.List;

public class GetNotificationWrapper {

  private List<GetNotificationEntity> notifications;

  public List<GetNotificationEntity> getNotifications() {
    return notifications;
  }

  public void setNotifications(List<GetNotificationEntity> notifications) {
    this.notifications = notifications;
  }

  @Override
  public String toString() {
    return "GetNotificationWrapper{" +
        "notifications=" + notifications +
        '}';
  }
}
