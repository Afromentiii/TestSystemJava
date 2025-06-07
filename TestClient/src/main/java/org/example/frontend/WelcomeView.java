package org.example.frontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Client;

public class WelcomeView extends VBox {
    private Runnable logout;
    private Runnable startTest;
    private Runnable showResults;
    private Client client;

    static class MyButton extends Button {
        public MyButton(String text) {
            super(text);
            this.setFont(Font.font("Arial", 18));
            this.setPrefSize(900, 50);
            this.setCursor(javafx.scene.Cursor.HAND);
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

    public WelcomeView(String firstName, String lastName) {
        setPadding(new Insets(50, 30, 30, 30));
        setSpacing(30);

        MyLabel title = new MyLabel("Welcome, " + firstName + " " + lastName + "!");
        MyButton logoutButton = new MyButton("Logout");
        logoutButton.setOnAction(e -> this.logout.run());

        MyButton startTestButton = new MyButton("Start test!");
        startTestButton.setOnAction(e -> this.startTest.run());

        MyButton showResultsButton = new MyButton("Show results");
        showResultsButton.setOnAction(e -> this.showResults.run());

        getChildren().addAll(title, startTestButton, showResultsButton, logoutButton);
    }

    public void setLogout(Runnable logout){
        this.logout = logout;
    }

    public void setStartTest(Runnable startTest){
        this.startTest = startTest;
    }

    public void setShowResults(Runnable showResults){this.showResults = showResults;}

}
