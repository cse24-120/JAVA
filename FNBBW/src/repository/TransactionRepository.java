package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Transaction;
import models.Account;
import Utils.DBConnection;

public class TransactionRepository {

    public static void addTransaction(Transaction txn, Account account) {
        String sql = "INSERT INTO transactions (transaction_id, account_id, transaction_date, type, amount, reference, balance) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, txn.getTransactionId());
            stmt.setInt(2, getAccountId(account));
            stmt.setDate(3, new java.sql.Date(txn.getDate().getTime()));
            stmt.setString(4, txn.getType());
            stmt.setDouble(5, txn.getAmount());
            stmt.setString(6, txn.getReference());
            stmt.setDouble(7, txn.getBalanceAfter());

            stmt.executeUpdate();
            System.out.println("Transaction saved to database.");

        } catch (SQLException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public static List<Transaction> getTransactionsByAccount(String accountNo) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.transaction_id, t.transaction_date, t.type, t.amount, t.reference, t.balance " +
                     "FROM transactions t " +
                     "JOIN accounts a ON t.account_id = a.account_id " +
                     "WHERE a.account_no = ? " +
                     "ORDER BY t.transaction_date ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("transaction_id");
                Date date = rs.getDate("transaction_date");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String reference = rs.getString("reference");
                double balance = rs.getDouble("balance");

                Transaction txn = new Transaction(id, date, type, amount, reference, balance);
                transactions.add(txn);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
        }

        return transactions;
    }

    private static int getAccountId(Account account) throws SQLException {
        String sql = "SELECT account_id FROM accounts WHERE account_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getAccountNo());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("account_id");
            } else {
                throw new SQLException("Account not found in database.");
            }
        }
    }
}