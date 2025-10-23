package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Main entry point for the FNBBW banking system.
 * Launches the sign-in page.
 */
public class FNBBW extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/BankSystemSignInPage.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("FNBBW - Sign In");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            showStartupError(e.getMessage());
        }
    }

    private void showStartupError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Startup Error");
        alert.setHeaderText("Unable to launch the application.");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
