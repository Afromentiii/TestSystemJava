package org.example.frontend;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.example.Question;

import java.util.function.Consumer;

public class QuestionView extends VBox {

    private ToggleGroup group = new ToggleGroup();
    private Consumer<String> responseCallback;


    public QuestionView(Question question, int questionID, int testLength) {
        Label title = new Label("Question " + (questionID ) + " / " + (testLength));
        Label ask = new Label(question.getQuestion());

        RadioButton answer1 = new RadioButton(question.getAnswerA());
        answer1.setUserData(question.getAnswerA());
        RadioButton answer2 = new RadioButton(question.getAnswerB());
        answer2.setUserData(question.getAnswerB());
        RadioButton answer3 = new RadioButton(question.getAnswerC());
        answer3.setUserData(question.getAnswerC());
        answer1.setToggleGroup(group);
        answer2.setToggleGroup(group);
        answer3.setToggleGroup(group);
        Button submit = new Button("Submit");

        submit.setOnAction(e -> this.responseCallback.accept(group.getSelectedToggle().getUserData().toString()));

        getChildren().addAll(title, ask, answer1, answer2, answer3, submit);
    }

    public void setResponseCallback(Consumer<String> callback) {
        this.responseCallback = callback;
    }

}
