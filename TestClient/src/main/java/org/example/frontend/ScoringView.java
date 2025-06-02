package org.example.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScoringView extends VBox{
    private Runnable backToWelcome;

    public void setBackToWelcome(Runnable callback){
        this.backToWelcome = callback;
    }

    public ScoringView(Integer points, Integer maxPoints){
        Label endLabel = new Label("Your score: " + points + " / " + maxPoints + " (" + (points * 100 / maxPoints) + "%)");
        VBox endScreen = new VBox(endLabel);
        Button backButton = new Button("End test");
        backButton.setOnAction(e -> {
            this.backToWelcome.run();
        });
        endScreen.setAlignment(Pos.CENTER);
        endScreen.setPadding(new Insets(20));
        getChildren().add(endScreen);
    }
}
