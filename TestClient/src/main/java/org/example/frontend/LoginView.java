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

    public LoginView(Stage stage, Client client) {
        setPadding(new Insets(10));
        setSpacing(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label statusLabel = new Label();

        loginButton.setOnAction(event -> {
            if(client.loginUser(usernameField.getText(), passwordField.getText()) && onLoginSuccess != null) {
                onLoginSuccess.run();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            this.onBackPressed.run();
        });

        getChildren().addAll(usernameField, passwordField, loginButton, statusLabel);
    }

    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }

    public void setOnBackPressed(Runnable onBackPressed) {
        this.onBackPressed = onBackPressed;
    }
}
