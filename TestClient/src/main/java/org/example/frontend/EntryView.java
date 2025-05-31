package org.example.frontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Client;

public class EntryView extends VBox {

    private Runnable onLogin;
    private Runnable onRegister;

    public EntryView(Stage stage, Client client) {

        setPadding(new Insets(30));
        setSpacing(10);

        Label title = new Label("Sign in or sign up");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {this.onLogin.run();});

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {this.onRegister.run();});


        getChildren().addAll(title, registerButton, loginButton);
    }

    public void setOnLogin(Runnable onLogin) {
        this.onLogin = onLogin;
    }

    public void setOnRegister(Runnable onRegister) {
        this.onRegister = onRegister;
    }
}

