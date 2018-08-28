package com.senacor.bankathon2018.connectors;

import me.figo.models.Transaction;

import java.util.List;

public class WrappedTransactions {

    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
