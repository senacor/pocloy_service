package com.senacor.bankathon2018.connectors.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import me.figo.models.AdditionalTransactionInfo;
import me.figo.models.Category;

public class Transaction {

  private String transaction_id;

  private String account_id;

  private String name;

  private String account_number;

  private String bank_code;

  private String bank_name;

  private BigDecimal amount;

  private String currency;

  private LocalDateTime booking_date;


  private LocalDateTime value_date;

  private String purpose;

  private String type;

  private String booking_text;

  private boolean booked;

  private boolean visited;

  private String transaction_code;

  private AdditionalTransactionInfo additional_info;

  private List<Category> categories;

  private String iban;

  private String bic;

  private String booking_key;

  private String sepa_purpose_code;

  private String sepa_remittance_info;

  public String getTransaction_id() {
    return transaction_id;
  }

  public void setTransaction_id(String transaction_id) {
    this.transaction_id = transaction_id;
  }

  public String getAccount_id() {
    return account_id;
  }

  public void setAccount_id(String account_id) {
    this.account_id = account_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccount_number() {
    return account_number;
  }

  public void setAccount_number(String account_number) {
    this.account_number = account_number;
  }

  public String getBank_code() {
    return bank_code;
  }

  public void setBank_code(String bank_code) {
    this.bank_code = bank_code;
  }

  public String getBank_name() {
    return bank_name;
  }

  public void setBank_name(String bank_name) {
    this.bank_name = bank_name;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public LocalDateTime getBooking_date() {
    return booking_date;
  }

  public void setBooking_date(LocalDateTime booking_date) {
    this.booking_date = booking_date;
  }

  public LocalDateTime getValue_date() {
    return value_date;
  }

  public void setValue_date(LocalDateTime value_date) {
    this.value_date = value_date;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBooking_text() {
    return booking_text;
  }

  public void setBooking_text(String booking_text) {
    this.booking_text = booking_text;
  }

  public boolean isBooked() {
    return booked;
  }

  public void setBooked(boolean booked) {
    this.booked = booked;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public String getTransaction_code() {
    return transaction_code;
  }

  public void setTransaction_code(String transaction_code) {
    this.transaction_code = transaction_code;
  }

  public AdditionalTransactionInfo getAdditional_info() {
    return additional_info;
  }

  public void setAdditional_info(AdditionalTransactionInfo additional_info) {
    this.additional_info = additional_info;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public String getBic() {
    return bic;
  }

  public void setBic(String bic) {
    this.bic = bic;
  }

  public String getBooking_key() {
    return booking_key;
  }

  public void setBooking_key(String booking_key) {
    this.booking_key = booking_key;
  }

  public String getSepa_purpose_code() {
    return sepa_purpose_code;
  }

  public void setSepa_purpose_code(String sepa_purpose_code) {
    this.sepa_purpose_code = sepa_purpose_code;
  }

  public String getSepa_remittance_info() {
    return sepa_remittance_info;
  }

  public void setSepa_remittance_info(String sepa_remittance_info) {
    this.sepa_remittance_info = sepa_remittance_info;
  }
}
