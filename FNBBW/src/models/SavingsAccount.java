package models;

import java.util.Date;

/**
 * Savings account with restricted withdrawals and monthly interest.
 */
public class SavingsAccount extends Account implements InterestBearing {

    public SavingsAccount(String accountNo, String branch) {
        super(accountNo, branch);
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
        System.out.println("Withdrawals are not allowed from a Savings Account.");
    }

    @Override
    public void calculateInterest() {
        double interest = balance * 0.0005; // 0.05% monthly
        balance += interest;
        transactions.add(new Transaction(
            "TXN" + System.currentTimeMillis(),
            new Date(),
            "Interest",
            interest,
            "Monthly Interest",
            balance
        ));
    }
}
