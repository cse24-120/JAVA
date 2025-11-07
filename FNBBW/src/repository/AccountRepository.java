package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.*;
import Utils.DBConnection;

public class AccountRepository {

    public static void addAccount(Account account, Customer customer) {
        String sql = "INSERT INTO accounts (customer_id, account_no, type, balance, company_name, company_address) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, getCustomerId(customer));
            stmt.setString(2, account.getAccountNo());
            stmt.setString(3, account.getType());
            stmt.setDouble(4, account.getBalance());

            if (account.getType().equalsIgnoreCase("cheque")) {
                stmt.setString(5, account.getCompanyName());
                stmt.setString(6, account.getCompanyAddress());
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }

            stmt.executeUpdate();
            System.out.println("Account added to database.");

        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
        }
    }

    public static List<Account> getAccountsByCustomer(Customer customer) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, getCustomerId(customer));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String accountNo = rs.getString("account_no");
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");

                Account account;
                switch (type.toLowerCase()) {
                    case "savings":
                        account = new SavingsAccount(accountNo, "Main Branch", balance);
                        break;
                    case "investment":
                        account = new InvestmentAccount(accountNo, "Standard", balance);
                        break;
                    case "cheque":
                        String companyName = rs.getString("company_name");
                        String companyAddress = rs.getString("company_address");
                        account = new ChequeAccount(accountNo, "Main Branch", balance, companyName, companyAddress);
                        break;
                    default:
                        continue;
                }

                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }

        return accounts;
    }

    public static void updateBalance(Account account) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, account.getBalance());
            stmt.setString(2, account.getAccountNo());
            stmt.executeUpdate();

            System.out.println("Balance updated for account: " + account.getAccountNo());

        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

    private static int getCustomerId(Customer customer) throws SQLException {
        String sql = "SELECT customer_id FROM customers WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getUsername());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("customer_id");
            } else {
                throw new SQLException("Customer not found in database.");
            }
        }
    }
}