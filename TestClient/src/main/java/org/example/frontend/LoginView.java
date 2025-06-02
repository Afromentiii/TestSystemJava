package org.example.frontend;

import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Client;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class LoginView extends VBox {
    static class MyButton extends Button {
        public MyButton(String text) {
            super(text);
            this.setFont(Font.font("Arial", 18));
            this.setPrefSize(900, 50);
            this.setCursor(javafx.scene.Cursor.HAND);
        }
    }

    static class MyTextField extends TextField {
        public MyTextField(String promptText) {
            super();
            this.setPromptText(promptText);
            this.setFont(Font.font("Arial", 18));
            this.setPrefSize(180, 50);
            this.setCursor(Cursor.TEXT);
        }
    }

    static class MyPasswordField extends PasswordField {
        public MyPasswordField(String promptText) {
            super();
            this.setPromptText(promptText);
            this.setFont(Font.font("Arial", 18));
            this.setPrefSize(180, 50);
            this.setCursor(Cursor.TEXT);
        }
    }

    static class MyLabel extends Label {
        public MyLabel(String text) {
            super(text);
            this.setFont(Font.font("Arial", 18));
            this.setPrefSize(500, 40);
            this.setPadding(new Insets(10));
            this.setAlignment(javafx.geometry.Pos.CENTER);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setBorder(new Border(new BorderStroke(
                    Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)
            )));
        }
    }

    private Runnable onLoginSuccess;
    private Runnable onBackPressed;
    private final MyPasswordField passwordField;
    private final MyTextField usernameField;

    public LoginView(Client client) {
        this.setPadding(new Insets(40, 20, 20, 20));
        this.setSpacing(40);
        this.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        usernameField = new MyTextField("Username");
        passwordField = new MyPasswordField("Password");
        MyLabel statusLabel = new MyLabel("Please login to continue!");
        MyButton loginButton = new MyButton("Login");
        loginButton.setOnAction(event -> {
            if(client.loginUser(usernameField.getText(), passwordField.getText()) && onLoginSuccess != null) {
                onLoginSuccess.run();
            } else {
                statusLabel.setText("Invalid username or password");
            }
        });

        MyButton backButton = new MyButton("Back");
        backButton.setOnAction(event -> this.onBackPressed.run());
        HBox buttons = new HBox(loginButton, backButton);
        buttons.setSpacing(20);
        getChildren().addAll(statusLabel, usernameField, passwordField, buttons);
    }

    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }
    public void setOnBackPressed(Runnable onBackPressed) {
        this.onBackPressed = onBackPressed;
    }
}
