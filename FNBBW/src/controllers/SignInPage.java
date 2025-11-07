package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Customer;
import repository.CustomerRepository;
import repository.AccountRepository;
import session.Session;

import java.io.IOException;
import java.util.List;

/**
 * Handles user sign-in and navigation to sign-up pages.
 */
public class SignInPage {

    @FXML private TextField UserNameTextField;
    @FXML private PasswordField PasswordTextField;
    @FXML private Button SignInButton;
    @FXML private MenuButton SignUpButton;
    @FXML private MenuItem IndividualCustomerButton;
    @FXML private MenuItem CompanyCustomerButton;

    private List<Customer> registeredCustomers = CustomerRepository.getAllCustomers();

    @FXML
    void SignInToDashboard(ActionEvent event) {
        String username = UserNameTextField.getText().trim();
        String password = PasswordTextField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Missing Information", "Please enter both username and password.");
            return;
        }

        for (Customer customer : registeredCustomers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                // Load accounts from database
                customer.setAccounts(AccountRepository.getAccountsByCustomer(customer));

                // Set session
                Session.setCurrentCustomer(customer);

                //  Navigate to dashboard
                switchScene("DashBoard.fxml", SignInButton);
                return;
            }
        }

        showAlert("Login Failed", "Invalid username or password.");
    }

    @FXML
    void IndividualSignUpPage(ActionEvent event) {
        switchScene("IndividualCustomerSignUp.fxml", SignUpButton);
    }

    @FXML
    void CompanySignUpPage(ActionEvent event) {
        switchScene("CompanyCustomerSignUp.fxml", SignUpButton);
    }

    private void switchScene(String fxmlFile, Control control) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/" + fxmlFile));
            Stage stage = (Stage) control.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
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