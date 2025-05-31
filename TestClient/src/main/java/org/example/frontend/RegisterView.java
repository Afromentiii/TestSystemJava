package org.example.frontend;

import javafx.util.Pair;
import org.example.Client;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView extends VBox {

    private Runnable onBackPressed;

    public RegisterView(Stage stage, Client client) {
        setPadding(new Insets(10));
        setSpacing(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("E-mail");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField passwordMatchField = new PasswordField();
        passwordMatchField.setPromptText("Confirm your password");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        Label statusLabel = new Label();

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String passMatch = passwordMatchField.getText();

            if (pass.equals(passMatch)) {
                Pair<Boolean, String> result = client.registerUser(user, pass, email, firstName, lastName);
                    statusLabel.setText(result.getValue());
            } else {
                statusLabel.setText("Passwords do not match");
            }

        });
        Button backButton = new Button("Back");
        backButton.setOnAction( e -> {
            this.onBackPressed.run();
        });
        getChildren().addAll(statusLabel, usernameField,  passwordField, passwordMatchField, firstNameField, lastNameField, emailField, registerButton, backButton);
    }

    public void setOnBackPressed(Runnable onBackPressed) {
        this.onBackPressed = onBackPressed;
    }
}
