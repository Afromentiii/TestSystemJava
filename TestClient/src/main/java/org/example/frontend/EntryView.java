package org.example.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.Client;



public class EntryView extends VBox {
    static class MyButton extends Button {
        public MyButton(String text) {
            super(text);
            this.setFont(Font.font("Arial", 20));
            this.setPrefSize(200, 50);
            this.setCursor(javafx.scene.Cursor.HAND);
        }
    }
    private Runnable onLogin;
    private Runnable onRegister;

    public EntryView() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(110));
        this.setSpacing(20);

        Label title = new Label("Sign in or sign up");
        title.setAlignment(javafx.geometry.Pos.CENTER);
        title.setPrefSize(300, 80);
        title.setFont(Font.font("Arial", 30));

        MyButton loginButton = new MyButton("Login");
        loginButton.setOnAction(e -> this.onLogin.run());

        MyButton registerButton = new MyButton("Register");
        registerButton.setOnAction(e -> this.onRegister.run());

        getChildren().addAll(title, registerButton, loginButton);
    }

    public void setOnLogin(Runnable onLogin) {
        this.onLogin = onLogin;
    }

    public void setOnRegister(Runnable onRegister) {
        this.onRegister = onRegister;
    }
}

