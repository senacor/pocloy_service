package com.senacor.bankathon2018.connectors.model.axway.push_notifications;

public class MetaInfoWrapper {

  private Meta meta;

  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  @Override
  public String toString() {
    return "MetaInfoWrapper{" +
        "meta=" + meta +
        '}';
  }
}
