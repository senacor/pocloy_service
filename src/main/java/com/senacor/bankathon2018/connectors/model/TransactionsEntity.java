package com.senacor.bankathon2018.connectors.model;

import java.util.List;

public class TransactionsEntity {

  private List<Transaction> transactions;

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }
}
