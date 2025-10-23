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
import models.Individual;
import repository.CustomerRepository;
import session.Session;

/**
 * Handles individual customer registration and navigation to account setup.
 */
public class IndividualSignUpController {

    @FXML private TextField FirstNameTextField;
    @FXML private TextField LastNameTextField;
    @FXML private TextField GenderTextField;
    @FXML private TextField PhoneNumberTextField;
    @FXML private TextField UserNameSignUpTextField;
    @FXML private TextField PasswordSignUpTextField;
    @FXML private Button IndividualNextButton;

    @FXML
    void IndividualAccountChoosingPage(ActionEvent event) {
        String firstName = FirstNameTextField.getText().trim();
        String lastName = LastNameTextField.getText().trim();
        String gender = GenderTextField.getText().trim();
        String phone = PhoneNumberTextField.getText().trim();
        String username = UserNameSignUpTextField.getText().trim();
        String password = PasswordSignUpTextField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty() ||
            phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields.");
            return;
        }

        if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
            showAlert("Invalid Gender", "Gender must be 'Male' or 'Female'.");
            return;
        }

        if (!phone.matches("\\d+")) {
            showAlert("Invalid Phone Number", "Phone number must contain only digits.");
            return;
        }

        int phoneNumber = Integer.parseInt(phone);
        Individual individual = new Individual(username, password, firstName, lastName, phoneNumber, gender);
        Session.setCurrentCustomer(individual);
        CustomerRepository.addCustomer(individual);

        switchScene("IndividualCustomerAccountOpen.fxml", IndividualNextButton);
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
