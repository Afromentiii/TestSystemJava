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
    private TextField usernameField, emailField, passwordField, passwordMatchField, firstNameField, lastNameField;


    public RegisterView(Stage stage, Client client) {
        setPadding(new Insets(10));
        setSpacing(10);

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        emailField = new TextField();
        emailField.setPromptText("E-mail");

         passwordField = new PasswordField();
        passwordField.setPromptText("Password");

         passwordMatchField = new PasswordField();
        passwordMatchField.setPromptText("Confirm your password");

         firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
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

    public void refresh() {
        this.usernameField.clear();
        this.passwordField.clear();
        this.passwordMatchField.clear();
        this.firstNameField.clear();
        this.lastNameField.clear();
        this.emailField.clear();
    }

    public void setOnBackPressed(Runnable onBackPressed) {
        this.onBackPressed = onBackPressed;
    }
}
