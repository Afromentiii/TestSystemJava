package org.example.frontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Question;

import java.util.function.Consumer;

public class QuestionView extends VBox {
    static class MyButton extends Button {
        public MyButton(String text) {
            super(text);
            this.setFont(Font.font("Arial", 24));
            this.setPrefSize(900, 50);
            this.setCursor(javafx.scene.Cursor.HAND);
        }
    }

    
    static class MyLabel extends Label {
        public MyLabel(String text, int size) {
            super(text);
            this.setFont(Font.font("Arial", size));
            this.setPrefSize(500, 90);
            this.setPadding(new Insets(10));
            this.setAlignment(javafx.geometry.Pos.CENTER);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            this.setBorder(new Border(new BorderStroke(
                    Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)
            )));
            this.setWrapText(true);
        }
    }
    
    static class MyToggle extends RadioButton {
        public MyToggle(String text, ToggleGroup group) {
            super(text);
            this.setUserData(text);
            this.setToggleGroup(group);
            this.setPrefSize(500, 40);
            this.setFont(new Font("Arial", 18));
        }
        
    }
    private final ToggleGroup group = new ToggleGroup();
    private Consumer<String> responseCallback;


    public QuestionView(Question question, int questionID, int testLength) {
        this.setPadding(new Insets(30));
        this.setSpacing(30);

        MyLabel title = new MyLabel("Question " + (questionID ) + " / " + (testLength), 14);
        MyLabel ask = new MyLabel(question.getQuestion(), 18);

        MyToggle answer1 = new MyToggle(question.getAnswerA(), group);
        MyToggle answer2 = new MyToggle(question.getAnswerB(), group);
        MyToggle answer3 = new MyToggle(question.getAnswerC(), group);
        MyButton submit = new MyButton("Submit");
        submit.setOnAction(e -> this.responseCallback.accept(group.getSelectedToggle().getUserData().toString()));
        getChildren().addAll(title, ask, answer1, answer2, answer3, submit);
    }

    public void setResponseCallback(Consumer<String> callback) {
        this.responseCallback = callback;
    }

}
