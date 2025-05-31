package org.example.frontend;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Client;

import javafx.scene.control.Label;

public class WelcomeView extends VBox {
    private Label title;
    private Runnable logout;
    private Runnable startTest;
    private Client client;

    public WelcomeView(Stage stage) {
        setPadding(new Insets(30));
        setSpacing(10);

        title = new Label();

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            this.client.logoutUser();
            this.client = null;
            this.logout.run();
        });

        Button startTestButton = new Button("Start test!");
        startTestButton.setOnAction(e -> {
            this.startTest.run();
        });

        getChildren().addAll(title, startTestButton, logoutButton);
    }

    public void refresh(Client client){
        this.client = client;
        title.setText("Welcome, " + this.client.getUser().getName());
    }

    public void setLogout(Runnable logout){
        this.logout = logout;
    }

    public void setStartTest(Runnable startTest){
        this.startTest = startTest;
    }

}
