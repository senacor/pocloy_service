package com.senacor.bankathon2018.connectors.model.figo;

public class CreateNotificationEntity {

  private String observe_key;
  private String notify_uri;
  private String state;

  public String getObserve_key() {
    return observe_key;
  }

  public void setObserve_key(String observe_key) {
    this.observe_key = observe_key;
  }

  public String getNotify_uri() {
    return notify_uri;
  }

  public void setNotify_uri(String notify_uri) {
    this.notify_uri = notify_uri;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
