package com.senacor.bankathon2018.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class LoyaltyCode {

  private String loyaltyCode;

  private String status;

  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss SS")
  private Date paymentDate;

  public LoyaltyCode(String loyaltyCode, String status, String content, Date paymentDate) {
    this.loyaltyCode = loyaltyCode;
    this.status = status;
    this.content = content;
    this.paymentDate = paymentDate;
  }

  public String getLoyaltyCode() {
    return loyaltyCode;
  }

  public void setLoyaltyCode(String loyaltyCode) {
    this.loyaltyCode = loyaltyCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }
}
