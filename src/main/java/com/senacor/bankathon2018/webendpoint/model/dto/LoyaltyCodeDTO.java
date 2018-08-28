package com.senacor.bankathon2018.webendpoint.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.senacor.bankathon2018.service.model.LoyaltyCode;
import java.time.LocalDateTime;

public class LoyaltyCodeDTO {

  private String loyaltyCode;

  private String status;

  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss SS")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime paymentDate;

  private String codeId;

  public LoyaltyCodeDTO(LoyaltyCode loyaltyCode) {
    this.loyaltyCode = loyaltyCode.getLoyaltyCode();
    this.status = loyaltyCode.getStatus().toString();
    this.content = loyaltyCode.getContent().toString();
    this.paymentDate = loyaltyCode.getPaymentDate();
    this.codeId = loyaltyCode.getPaymentTransactionId();
  }

  public LoyaltyCodeDTO(String loyaltyCode, String status, String content,
      LocalDateTime paymentDate,
      String codeId) {
    this.loyaltyCode = loyaltyCode;
    this.status = status;
    this.content = content;
    this.paymentDate = paymentDate;
    this.codeId = codeId;
  }

  public String getLoyaltyCode() {
    return loyaltyCode;
  }

  public void setLoyaltyCode(String loyaltyCode) {
    this.loyaltyCode = loyaltyCode;
  }

  public String getCodeId() {
    return codeId;
  }

  public void setCodeId(String codeId) {
    this.codeId = codeId;
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

  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }
}
