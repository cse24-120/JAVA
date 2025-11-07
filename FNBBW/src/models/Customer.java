package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all customers.
 * Encapsulates shared login and account management logic.
 */
public abstract class Customer {
    protected String username;
    protected String password;
    protected List<Account> accounts;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public abstract void openAccount(String accountType, double initialDeposit, String companyName, String companyAddress);

    public void depositToAccount(Account account, double amount) {
        if (account != null) account.deposit(amount);
    }

    public void withdrawFromAccount(Account account, double amount) {
        if (account != null) account.withdraw(amount);
    }

    public List<Transaction> viewTransactionHistory(Account account) {
        return account != null ? account.getTransactions() : new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }
    
   public void setAccounts(List<Account> accounts) {
       this.accounts = accounts;
   }
   
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
