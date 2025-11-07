package models;

import java.util.Date;

/**
 * Cheque account for employed individuals or companies.
 * Does not earn interest.
 */
public class ChequeAccount extends Account {

    private String companyName;
    private String companyAddress;
    
    public ChequeAccount(String accountNo, String branch, String companyName, String companyAddress) {
    super(accountNo, branch);
    this.companyName = companyName;
    this.companyAddress = companyAddress;
}

   public ChequeAccount(String accountNo, String branch, double balance, String companyName, String companyAddress) {
       super(accountNo, branch);
       this.balance = balance;
       this.companyName = companyName;
       this.companyAddress = companyAddress;
   }

    @Override
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction(
            "TXN" + System.currentTimeMillis(),
            new Date(),
            "Deposit",
            amount,
            "System Deposit",
            balance
        ));
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction(
                "TXN" + System.currentTimeMillis(),
                new Date(),
                "Withdraw",
                amount,
                "System Withdrawal",
                balance
            ));
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    @Override
    public void calculateInterest() {
        // Cheque accounts do not earn interest
    }
   @Override
   public String getType() {
       return "cheque";
   }
}
