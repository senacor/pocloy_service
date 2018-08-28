package com.senacor.bankathon2018.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ExchangeOffer {

  @Id
  @GeneratedValue
  private Long id;

}
