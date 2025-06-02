package org.example.frontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class StartTestView extends VBox {
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
            this.setFont(Font.font("Arial", 20));
            this.setPrefSize(500, 40);
            this.setPadding(new Insets(10));
            this.setAlignment(javafx.geometry.Pos.CENTER);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setBorder(new Border(new BorderStroke(
                    Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)
            )));
        }
    }
    MyButton yesButton, noButton;
    Runnable yesButtonCallback, noButtonCallback;

    public StartTestView() {
        setPadding(new Insets(30));
        setSpacing(30);

        MyLabel confirmation = new MyLabel("Do you really want to start test?");
        yesButton = new MyButton("Yes");
        yesButton.setOnAction(e -> {
            yesButtonCallback.run();
        });

        noButton = new MyButton("No");
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
