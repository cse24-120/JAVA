package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Account;
import models.Transaction;
import session.Session;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class transactionController implements Initializable {

    @FXML private TableView<Transaction> TransactionHistoryTable;
    @FXML private TableColumn<Transaction, String> IdColumn;
    @FXML private TableColumn<Transaction, String> DateColumn;
    @FXML private TableColumn<Transaction, String> TransactionTypeColumn;
    @FXML private TableColumn<Transaction, Double> AmountColumn;
    @FXML private TableColumn<Transaction, String> ReferenceColumn;
    @FXML private TableColumn<Transaction, Double> balanceColumn;

    @FXML private ComboBox<Account> FilterByDropDown;
    @FXML private Button DashboardButton;
    @FXML private Button TransactButton;
    @FXML private Button SignOutButton;
    @FXML private Button FilterButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FilterByDropDown.getItems().addAll(Session.getCurrentCustomer().getAccounts());

        // Set up column bindings
        IdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTransactionId()));
        DateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        TransactionTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));
        AmountColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getAmount()).asObject());
        ReferenceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReference()));
        balanceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getBalanceAfter()).asObject());
    }

    @FXML
    void FilterBy(ActionEvent event) {
        Account selected = FilterByDropDown.getValue();
        if (selected != null) {
            TransactionHistoryTable.getItems().clear();
            TransactionHistoryTable.getItems().addAll(selected.getTransactions());
        }
    }

    @FXML
    void GoToDashboardPage(ActionEvent event) {
        switchScene("DashBoard.fxml", DashboardButton);
    }

    @FXML
    void GoToTransactPage(ActionEvent event) {
        switchScene("Transact.fxml", TransactButton);
    }

    @FXML
    void GoToSignInPage(ActionEvent event) {
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
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
