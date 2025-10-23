package models;

import java.util.List;

/**
 * Represents an individual customer with personal details.
 */
public class Individual extends Customer {
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String gender;

    public Individual(String username, String password, String firstName, String lastName, int phoneNumber, String gender) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    /**
     * Opens a new account based on type and deposit.
     */
    @Override
    public void openAccount(String accountType, double initialDeposit,
                            String companyName, String companyAddress) {
        String accountNo = AccountUtils.generateAccountNo();
        String branch = AccountUtils.assignBranch(accountType);

        Account account;
        switch (accountType.toLowerCase()) {
            case "savings":
                account = new SavingsAccount(accountNo, branch);
                account.deposit(initialDeposit);
                break;
            case "investment":
                account = new InvestmentAccount(accountNo, branch, initialDeposit);
                break;
            case "cheque":
                if (companyName == null || companyAddress == null) {
                    throw new IllegalArgumentException("Employment details required for Cheque Account.");
                }
                account = new ChequeAccount(accountNo, branch, companyName, companyAddress);
                account.deposit(initialDeposit);
                break;
            default:
                throw new IllegalArgumentException("Invalid account type");
        }
        accounts.add(account);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
