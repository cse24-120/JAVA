import java.util.List;

public class BankingSystemTest {
    public static void main(String[] args) {
        // Create an Individual customer
        Individual individual = new Individual(
            "thabo123", "securePass", "Thabo", "Molefe", 71234567, "Male"
        );

        // Create a Company customer
        Company company = new Company(
            "btcl_admin", "adminPass", "Botswana Telecom", "BTCL123456",
            "Mpho Dube", 3950000
        );

        // INDIVIDUAL opens accounts
        individual.openAccount("savings", 0.0, null, null);
        individual.openAccount("investment", 500.0, null, null);
        individual.openAccount("cheque", 0.0, "Botswana Telecom", "CBD, Gaborone");

        // COMPANY opens accounts
        company.openAccount("savings", 0.0, null, null);
        company.openAccount("investment", 1000.0, null, null);
        company.openAccount("cheque", 0.0, "BTCL HQ", "CBD, Gaborone");

        // Transactions for INDIVIDUAL
        Account indInvestment = individual.getAccounts().get(1); // investment
        individual.depositToAccount(indInvestment, 200.0);
        individual.withdrawFromAccount(indInvestment, 100.0);

        Account indSavings = individual.getAccounts().get(0); // savings
        individual.depositToAccount(indSavings, 1000.0);
        individual.withdrawFromAccount(indSavings, 50.0); // Should be blocked

        // Transactions for COMPANY
        Account compInvestment = company.getAccounts().get(1); // investment
        company.depositToAccount(compInvestment, 500.0);
        company.withdrawFromAccount(compInvestment, 300.0);

        Account compSavings = company.getAccounts().get(0); // savings
        company.depositToAccount(compSavings, 2000.0);

        // Calculate interest for all interest-bearing accounts
        for (Account acc : individual.getAccounts()) {
            if (acc instanceof InterestBearing) {
                ((InterestBearing) acc).calculateInterest();
            }
        }

        for (Account acc : company.getAccounts()) {
            if (acc instanceof InterestBearing) {
                ((InterestBearing) acc).calculateInterest();
            }
        }

        // View transaction history
        System.out.println("\nIndividual - Investment Account Transactions:");
        for (Transaction txn : individual.viewTransactionHistory(indInvestment)) {
            System.out.println(txn.getTransactionDetails());
        }

        System.out.println("\nCompany - Investment Account Transactions:");
        for (Transaction txn : company.viewTransactionHistory(compInvestment)) {
            System.out.println(txn.getTransactionDetails());
        }

        // Display account numbers and branches
        System.out.println("\nIndividual Accounts:");
        for (Account acc : individual.getAccounts()) {
            System.out.println(acc.getAccountNo() + " | " + acc.getBranch() + " | Balance: " + acc.getBalance());
        }

        System.out.println("\nCompany Accounts:");
        for (Account acc : company.getAccounts()) {
            System.out.println(acc.getAccountNo() + " | " + acc.getBranch() + " | Balance: " + acc.getBalance());
        }
    }
}
