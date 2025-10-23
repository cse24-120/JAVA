package models;

import java.util.Date;

/**
 * Represents a single transaction on an account.
 */
public class Transaction {
    private String transactionId;
    private Date date;
    private String type;           // "Deposit" or "Withdrawal"
    private double amount;
    private String reference;
    private double balanceAfter;

    public Transaction(String transactionId, Date date, String type, double amount, String reference, double balanceAfter) {
        this.transactionId = transactionId;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.reference = reference;
        this.balanceAfter = balanceAfter;
    }

    // Getters for TableView binding
    public String getTransactionId() {
        return transactionId;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getReference() {
        return reference;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    // Optional: for logging or ListView fallback
    public String getTransactionDetails() {
        return String.format("ID: %s | Date: %s | Type: %s | Amount: %.2f | Ref: %s | Balance: %.2f",
                transactionId, date.toString(), type, amount, reference, balanceAfter);
    }

    @Override
    public String toString() {
        return getTransactionDetails();
    }
}
