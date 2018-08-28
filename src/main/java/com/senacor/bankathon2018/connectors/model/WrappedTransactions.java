package com.senacor.bankathon2018.connectors.model;

import java.util.List;
import me.figo.models.Transaction;

public class WrappedTransactions {

  private List<Transaction> transactions;

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }
}
