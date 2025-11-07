package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all account types.
 * Encapsulates shared attributes and behaviors.
 */
public abstract class Account {
    protected String accountNo;
    protected double balance;
    protected String branch;
    protected List<Transaction> transactions;
    protected String companyName;//cheque
    protected String companyAddress;//cheque



    public Account(String accountNo, String branch) {
        this.accountNo = accountNo;
        this.branch = branch;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Abstract methods to be implemented by subclasses
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract void calculateInterest();
    public abstract String getType();


    // Transaction management
    public void addTransaction(Transaction txn) {
        transactions.add(txn);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Accessors
    public String getAccountNo() {
        return accountNo;
    }

    public String getBranch() {
        return branch;
    }

    public double getBalance() {
        return balance;
    }

   // Cheque accessors
   public String getCompanyName() {
       return companyName;
   }
   
   public String getCompanyAddress() {
       return companyAddress;
   }


    // Display format for ComboBox or logs
    @Override
    public String toString() {
        return getClass().getSimpleName().replace("Account", " Account") + " - " + accountNo;
    }
}
