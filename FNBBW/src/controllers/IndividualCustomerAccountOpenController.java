package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import models.Customer;
import models.Individual;
import repository.CustomerRepository;
import session.Session;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles account creation for individual customers and autofills personal details.
 */
public class IndividualCustomerAccountOpenController implements Initializable {

    // Personal details (autofilled)
    @FXML private TextField IndividualFirstName;
    @FXML private TextField IndividualLastName;
    @FXML private TextField IndividualGender;
    @FXML private TextField IndividualPhoneNmber;

    // Savings account
    @FXML private CheckBox IndividualSavingsOpenButton;
    @FXML private TextField IndividualSavingsDepositAmountTextField;

    // Cheque account
    @FXML private CheckBox IndividualChequeOpenButton;
    @FXML private TextField IndividualChequeDepositTextAmountField;
    @FXML private TextField IndividualChequeCompanyNameTextField;
    @FXML private TextField IndividualChequeCompanyAddress;

    // Investment account
    @FXML private CheckBox IndividualInvestmentOpenButton;
    @FXML private TextField IndividualInvestmentDepositAmountTextField;

    @FXML private Button IndividualSignUpFinishButton;

    /**
     * Autofills personal details from session.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Individual customer = (Individual) Session.getCurrentCustomer();
        IndividualFirstName.setText(customer.getFirstName());
        IndividualLastName.setText(customer.getLastName());
        IndividualGender.setText(customer.getGender());
        IndividualPhoneNmber.setText(String.valueOf(customer.getPhoneNumber()));
    }

    /**
     * Handles account creation and navigation to sign-in.
     */
    @FXML
    void IndividualFinishSignUp(ActionEvent event) {
        Customer customer = Session.getCurrentCustomer();

        try {
            if (IndividualSavingsOpenButton.isSelected()) {
                double deposit = Double.parseDouble(IndividualSavingsDepositAmountTextField.getText().trim());
                customer.openAccount("savings", deposit, null, null);
            }

            if (IndividualChequeOpenButton.isSelected()) {
                double deposit = Double.parseDouble(IndividualChequeDepositTextAmountField.getText().trim());
                String companyName = IndividualChequeCompanyNameTextField.getText().trim();
                String companyAddress = IndividualChequeCompanyAddress.getText().trim();

                if (companyName.isEmpty() || companyAddress.isEmpty()) {
                    showAlert("Missing Info", "Company name and address are required for Cheque Account.");
                    return;
                }

                customer.openAccount("cheque", deposit, companyName, companyAddress);
            }

            if (IndividualInvestmentOpenButton.isSelected()) {
                double deposit = Double.parseDouble(IndividualInvestmentDepositAmountTextField.getText().trim());
                if (deposit < 500) {
                    showAlert("Minimum Investment", "Investment deposit must be at least 500.");
                    return;
                }
                customer.openAccount("investment", deposit, null, null);
            }

            CustomerRepository.addCustomer(customer);
            showAlert("Success", "Account(s) created successfully. Please sign in to continue.");
            switchScene("BankSystemSignInPage.fxml", IndividualSignUpFinishButton);

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numeric values for deposits.");
        } catch (Exception e) {
            showAlert("Account Error", e.getMessage());
            e.printStackTrace();
        }
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
