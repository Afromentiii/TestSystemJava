package org.example.frontend;

import org.example.Client;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class LoginView extends VBox {

    private Button loginButton = new Button("Login");
    private Runnable onLoginSuccess;
    private Runnable onBackPressed;
    private PasswordField passwordField;
    private TextField usernameField;

    public LoginView(Client client) {
        setPadding(new Insets(10));
        setSpacing(10);

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label statusLabel = new Label();

        loginButton.setOnAction(event -> {
            if(client.loginUser(usernameField.getText(), passwordField.getText()) && onLoginSuccess != null) {
                onLoginSuccess.run();
            } else {
                statusLabel.setText("Invalid username or password");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            this.onBackPressed.run();
        });

        getChildren().addAll(statusLabel, usernameField, passwordField, loginButton, backButton);
    }

    public void refresh() {
        this.passwordField.clear();
        this.usernameField.clear();
    }

    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }

    public void setOnBackPressed(Runnable onBackPressed) {
        this.onBackPressed = onBackPressed;
    }
}
