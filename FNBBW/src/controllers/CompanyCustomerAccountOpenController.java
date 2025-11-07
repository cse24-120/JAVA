package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import models.Company;
import models.Customer;
import repository.CustomerRepository;
import repository.AccountRepository;
import session.Session;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles account creation for company customers and autofills company details.
 */
public class CompanyCustomerAccountOpenController implements Initializable {

    // Company details (autofilled)
    @FXML private TextField CRN;
    @FXML private TextField CompanyName;
    @FXML private TextField CompanyDirectorName;
    @FXML private TextField CompanyTelephone;

    // Savings account
    @FXML private CheckBox CompanySavingsOpenButton;
    @FXML private TextField CompanySavingsDepositAmountTextField;

    // Cheque account
    @FXML private CheckBox CompanyChequeOpenButton;
    @FXML private TextField CompanyChequeDepositAmountTextField;
    @FXML private TextField CompanyChequeCopanyNameField;
    @FXML private TextField CompanyChequeCompanyAddressField;

    // Investment account
    @FXML private CheckBox CompanyInvestmentOpenButton;
    @FXML private TextField CompanyInvestmentDepositAmmountTextField;

    @FXML private Button CompanySignUpFinish;

    /**
     * Autofills company details from session.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Company company = (Company) Session.getCurrentCustomer();
        CRN.setText(company.getCompanyRegNumber());
        CompanyName.setText(company.getCompanyName());
        CompanyDirectorName.setText(company.getDirectorName());
        CompanyTelephone.setText(String.valueOf(company.getTelephone()));
    }

    /**
     * Handles account creation and navigation to sign-in.
     */
    @FXML
    void CompanyFinishSignUp(ActionEvent event) {
        Customer customer = Session.getCurrentCustomer();

        try {
            if (CompanySavingsOpenButton.isSelected()) {
                double deposit = Double.parseDouble(CompanySavingsDepositAmountTextField.getText().trim());
                customer.openAccount("savings", deposit, null, null);
                AccountRepository.addAccount(customer.getAccounts().get(customer.getAccounts().size() - 1), customer);
            }

            if (CompanyChequeOpenButton.isSelected()) {
                double deposit = Double.parseDouble(CompanyChequeDepositAmountTextField.getText().trim());
                String companyName = CompanyChequeCopanyNameField.getText().trim();
                String companyAddress = CompanyChequeCompanyAddressField.getText().trim();

                if (companyName.isEmpty() || companyAddress.isEmpty()) {
                    showAlert("Missing Info", "Company name and address are required for Cheque Account.");
                    return;
                }

                customer.openAccount("cheque", deposit, companyName, companyAddress);
                AccountRepository.addAccount(customer.getAccounts().get(customer.getAccounts().size() - 1), customer);
            }

            if (CompanyInvestmentOpenButton.isSelected()) {
                double deposit = Double.parseDouble(CompanyInvestmentDepositAmmountTextField.getText().trim());
                if (deposit < 500) {
                    showAlert("Minimum Investment", "Investment deposit must be at least 500.");
                    return;
                }
                customer.openAccount("investment", deposit, null, null);
                AccountRepository.addAccount(customer.getAccounts().get(customer.getAccounts().size() - 1), customer);
            }

            
            showAlert("Success", "Account(s) created successfully. Please sign in to continue.");
            switchScene("BankSystemSignInPage.fxml", CompanySignUpFinish);

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
