package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import models.Account;
import models.Customer;
import models.SavingsAccount;
import models.Transaction;
import session.Session;
import repository.TransactionRepository;
import repository.AccountRepository;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class transactController implements Initializable {

    @FXML private ComboBox<Account> WithdrawAccDropDown;
    @FXML private ComboBox<Account> DepositAccDropDown;
    @FXML private TextField DepositAmountTextField;
    @FXML private TextField WithdrawAmountTextField;
    @FXML private TextField DepositReference;
    @FXML private TextField WithdrawReference;
    @FXML private DatePicker DepositDateTextField;
    @FXML private DatePicker WithdrawDateTextField;
    @FXML private Button confirmDepositButton;
    @FXML private Button confirmWithdrawButton;
    @FXML private Button DashboardButton;
    @FXML private Button SignOutButton;
    @FXML private Button TransactionButton;

    private Customer customer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customer = Session.getCurrentCustomer();
        WithdrawAccDropDown.getItems().addAll(customer.getAccounts());
        DepositAccDropDown.getItems().addAll(customer.getAccounts());
    }

    @FXML
    private void CornfirmDeposit(ActionEvent event) {
        Account selected = DepositAccDropDown.getValue();
        double amount = parseAmount(DepositAmountTextField);
        String reference = DepositReference.getText().trim();
        Date date = java.sql.Date.valueOf(DepositDateTextField.getValue());

        if (selected != null && amount > 0 && reference != null && !reference.isEmpty() && date != null) {
            selected.deposit(amount);
            AccountRepository.updateBalance(selected);
            Transaction txn = new Transaction(
                UUID.randomUUID().toString(),
                date,
                "Deposit",
                amount,
                reference,
                selected.getBalance()
            );
            selected.addTransaction(txn);
            TransactionRepository.addTransaction(txn, selected);
            showAlert("Deposit Successful", "Deposited BWP " + String.format("%.2f", amount), Alert.AlertType.INFORMATION);
            clearFields();
        } else {
            showAlert("Deposit Failed", "Please fill all fields correctly.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void CornfirmWithdraw(ActionEvent event) {
        Account selected = WithdrawAccDropDown.getValue();
        double amount = parseAmount(WithdrawAmountTextField);
        String reference = WithdrawReference.getText().trim();
        Date date = java.sql.Date.valueOf(WithdrawDateTextField.getValue());

        if (selected != null && amount > 0 && reference != null && !reference.isEmpty() && date != null) {
            if (selected instanceof SavingsAccount) {
                showAlert("Withdrawal Blocked", "Withdrawals are not allowed from a Savings Account.", Alert.AlertType.WARNING);
            } else {
                selected.withdraw(amount);
                AccountRepository.updateBalance(selected);
                Transaction txn = new Transaction(
                    UUID.randomUUID().toString(),
                    date,
                    "Withdrawal",
                    amount,
                    reference,
                    selected.getBalance()
                );
                selected.addTransaction(txn);
                TransactionRepository.addTransaction(txn, selected);
                showAlert("Withdrawal Successful", "Withdrew BWP " + String.format("%.2f", amount), Alert.AlertType.INFORMATION);
            }
            clearFields();
        } else {
            showAlert("Withdrawal Failed", "Please fill all fields correctly.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void GoToDashboardPage(ActionEvent event) {
        switchScene("DashBoard.fxml", DashboardButton);
    }

    @FXML
    void GoToSignInPage() {
        switchScene("BankSystemSignInPage.fxml", SignOutButton);
    }

    @FXML
    void GoToTransactionPage() {
        switchScene("Transactions.fxml", TransactionButton);
    }

    private void switchScene(String fxmlFile, Control control) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/views/" + fxmlFile));
            Stage stage = (Stage) control.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert("Navigation Error", "Unable to load page: " + fxmlFile, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private double parseAmount(TextField field) {
        try {
            if (field != null && !field.getText().trim().isEmpty()) {
                return Double.parseDouble(field.getText().trim());
            }
        } catch (NumberFormatException e) {
            // Ignore and fall through
        }
        return -1;
    }

    private void clearFields() {
        DepositAmountTextField.clear();
        WithdrawAmountTextField.clear();
        DepositReference.clear();
        WithdrawReference.clear();
        WithdrawDateTextField.setValue(null);
        DepositDateTextField.setValue(null);
        DepositAccDropDown.getSelectionModel().clearSelection();
        WithdrawAccDropDown.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
