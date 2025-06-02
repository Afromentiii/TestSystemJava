package org.example.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoringView extends VBox{
    static class MyButton extends Button {
        public MyButton(String text) {
            super(text);
            this.setFont(Font.font("Arial", 18));
            this.setPrefSize(400, 50);
            this.setCursor(javafx.scene.Cursor.HAND);
        }
    }
    static class MyLabel extends Label {
        public MyLabel(String text) {
            super(text);
            this.setFont(Font.font("Arial", 20));
            this.setPrefSize(400, 40);
            this.setPadding(new Insets(10));
            this.setAlignment(javafx.geometry.Pos.CENTER);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setBorder(new Border(new BorderStroke(
                    Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)
            )));
        }
    }
    private Runnable backToWelcome;

    public void setBackToWelcome(Runnable callback){
        this.backToWelcome = callback;
    }

    public ScoringView(Integer points, Integer maxPoints){
        this.setSpacing(30);
        this.setPadding(new Insets(30));

        MyLabel endLabel = new MyLabel("Your score: " + points + " / " + maxPoints + " (" + (points * 100 / maxPoints) + "%)");
        MyButton backButton = new MyButton("End test");
        backButton.setOnAction(e -> {
            this.backToWelcome.run();
        });
//        VBox endScreen = new VBox(endLabel, backButton);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        getChildren().addAll(endLabel, backButton);
    }
}
