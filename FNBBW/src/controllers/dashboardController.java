package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;
import session.Session;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Displays account summaries and total balance for the signed-in customer.
 */
public class dashboardController implements Initializable {

    @FXML private TextField SavingsAccountNoField;
    @FXML private TextField SavingsBalanceField;

    @FXML private TextField InvestmentAccountNoField;
    @FXML private TextField InvestmentBalanceField;

    @FXML private TextField ChequeAccountNoField;
    @FXML private TextField ChequeBalanceField;

    @FXML private TextField TotalBalanceField;

    @FXML private Button TransactButton;
    @FXML private Button TransactionButton;
    @FXML private Button SignOutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Customer customer = Session.getCurrentCustomer();
        double total = 0.0;

        for (Account acc : customer.getAccounts()) {
            total += acc.getBalance();

            if (acc instanceof SavingsAccount) {
                SavingsAccountNoField.setText(acc.getAccountNo());
                SavingsBalanceField.setText(String.format("%.2f", acc.getBalance()));
            } else if (acc instanceof InvestmentAccount) {
                InvestmentAccountNoField.setText(acc.getAccountNo());
                InvestmentBalanceField.setText(String.format("%.2f", acc.getBalance()));
            } else if (acc instanceof ChequeAccount) {
                ChequeAccountNoField.setText(acc.getAccountNo());
                ChequeBalanceField.setText(String.format("%.2f", acc.getBalance()));
            }
        }

        TotalBalanceField.setText(String.format("%.2f", total));
    }

    @FXML
    void GoToTransactPage() {
        switchScene("Transact.fxml", TransactButton);
    }

    @FXML
    void GoToTransactionPage() {
        switchScene("Transactions.fxml", TransactionButton);
    }

    @FXML
    void GoToSignInPage() {
        switchScene("BankSystemSignInPage.fxml", SignOutButton);
    }

    private void switchScene(String fxmlFile, Control control) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/views/" + fxmlFile));
            Stage stage = (Stage) control.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert("Navigation Error", "Unable to load page: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
