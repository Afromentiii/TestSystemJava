package org.example.frontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Client;

import javafx.scene.control.Label;

public class StartTestView extends VBox {

    Button yesButton, noButton;
    Runnable yesButtonCallback, noButtonCallback;

    public StartTestView(Stage stage) {
        setPadding(new Insets(30));
        setSpacing(10);

        Label confirmation = new Label("Do you really want to start test?");
        yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            yesButtonCallback.run();
        });

        noButton = new Button("No");
        noButton.setOnAction(e -> {
            noButtonCallback.run();
        });

        getChildren().addAll(confirmation, yesButton, noButton);

    }
    public void setYesButtonCallback(Runnable callback) {
        yesButtonCallback = callback;
    }

    public void setNoButtonCallback(Runnable callback) {
        noButtonCallback = callback;
    }



}
