package models;

import java.util.Date;

/**
 * Investment account with interest and minimum deposit requirement.
 */
public class InvestmentAccount extends Account implements InterestBearing {

    public InvestmentAccount(String accountNo, String branch, double initialDeposit) {
        super(accountNo, branch);
        if (initialDeposit < 500.0) {
            throw new IllegalArgumentException("Initial deposit must be at least BWP500.00");
        }
        deposit(initialDeposit);
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
        double interest = balance * 0.05; // 5% monthly interest
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
