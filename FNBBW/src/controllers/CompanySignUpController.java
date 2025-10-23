package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import models.Company;
import repository.CustomerRepository;
import session.Session;

/**
 * Handles company registration and navigation to account setup.
 */
public class CompanySignUpController {

    @FXML private TextField CRNTextField;
    @FXML private TextField CompanyNameTextField;
    @FXML private TextField DirectorNameTextField;
    @FXML private TextField SignUpUserNameTextField;
    @FXML private TextField SignUpPasswordTextField;
    @FXML private TextField TelephoneTextField;
    @FXML private Button CompanyNextButton;

    @FXML
    void CompanyAccountChoosingPage(ActionEvent event) {
        String crn = CRNTextField.getText().trim();
        String companyName = CompanyNameTextField.getText().trim();
        String directorName = DirectorNameTextField.getText().trim();
        String username = SignUpUserNameTextField.getText().trim();
        String password = SignUpPasswordTextField.getText().trim();
        String telephoneStr = TelephoneTextField.getText().trim();

        if (crn.isEmpty() || companyName.isEmpty() || directorName.isEmpty() ||
            username.isEmpty() || password.isEmpty() || telephoneStr.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields.");
            return;
        }

        if (!telephoneStr.matches("\\d+")) {
            showAlert("Invalid Telephone", "Telephone number must contain only digits.");
            return;
        }

        int telephone = Integer.parseInt(telephoneStr);
        Company company = new Company(username, password, companyName, crn, directorName, telephone);
        Session.setCurrentCustomer(company);
        CustomerRepository.addCustomer(company);

        switchScene("CompanyCustomerAccountOpen.fxml", CompanyNextButton);
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
