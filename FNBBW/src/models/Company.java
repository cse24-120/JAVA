package models;

import java.util.List;

/**
 * Represents a company customer with registration and contact details.
 */
public class Company extends Customer {
    private String companyName;
    private String companyRegNumber;
    private String directorName;
    private int telephone;

    public Company(String username, String password, String companyName, String companyRegNumber,
                   String directorName, int telephone) {
        super(username, password);
        this.companyName = companyName;
        this.companyRegNumber = companyRegNumber;
        this.directorName = directorName;
        this.telephone = telephone;
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyRegNumber() {
        return companyRegNumber;
    }

    public String getDirectorName() {
        return directorName;
    }

    public int getTelephone() {
        return telephone;
    }
}
