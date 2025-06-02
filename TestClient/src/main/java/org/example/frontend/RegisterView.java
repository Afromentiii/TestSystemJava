package org.example.frontend;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;
import org.example.Client;


public class RegisterView extends VBox {
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
            this.setFont(Font.font("Arial", 14));
            this.setPrefSize(500, 40);
            this.setPadding(new Insets(10));
            this.setAlignment(javafx.geometry.Pos.CENTER);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setBorder(new Border(new BorderStroke(
                    Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)
            )));
        }
    }

    private Runnable onBackPressed;
    private final MyTextField usernameField, firstNameField, lastNameField, emailField;
    private final PasswordField passwordField, passwordMatchField;
    private final Label statusLabel;
    private final Client client;

    public void sendRegisterRequest() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String passMatch = passwordMatchField.getText();

        if (user.isEmpty()) statusLabel.setText("Username cannot be empty");
        else if (pass.length() < 4) statusLabel.setText("Password must be at least 4 characters long");
        else if (pass.equals(passMatch)) {
            Pair<Boolean, String> result = client.registerUser(user, pass, email, firstName, lastName);
            statusLabel.setText(result.getValue());
        } else {
            statusLabel.setText("Passwords do not match");
        }
    }

    public RegisterView(Client client) {
        this.client = client;

        setPadding(new Insets(10));
        setSpacing(15);

        usernameField = new MyTextField("Username");
        emailField = new MyTextField("E-mail");
        passwordField = new MyPasswordField("Password");
        passwordMatchField = new MyPasswordField("Confirm your password");
        firstNameField = new MyTextField("First Name");
        lastNameField = new MyTextField("Last Name");
        statusLabel = new MyLabel("Please register new user!");

        MyButton registerButton = new MyButton("Register");
        MyButton backButton = new MyButton("Back");
        registerButton.setOnAction(e -> sendRegisterRequest());
        backButton.setOnAction( e -> this.onBackPressed.run());
        HBox buttons = new HBox(registerButton, backButton);
        buttons.setSpacing(20);
        getChildren().addAll(statusLabel,
                usernameField,
                passwordField,
                passwordMatchField,
                firstNameField,
                lastNameField,
                emailField,
                buttons);
    }

    public void setOnBackPressed(Runnable onBackPressed) {
        this.onBackPressed = onBackPressed;
    }
}
