package org.example.frontend;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Client;

import javafx.scene.control.Label;

public class WelcomeView extends VBox {

    public WelcomeView(Stage stage, Client client) {
        Label label = new Label("Welcome" + client.getUser().getName() + " !");
        getChildren().add(label);
    }
}
