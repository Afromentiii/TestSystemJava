package org.example.frontend;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Client;
import org.example.Question;

public class QuestionView extends VBox {
    private Client client;
    private Label title, ask;
    private RadioButton answer1, answer2, answer3;
    private Button submit;
    private ToggleGroup group;
    private Runnable submitCallback;


    public QuestionView(Stage stage) {
        title = new Label();
        ask = new Label();
        answer1= new RadioButton();
        answer2 = new RadioButton();
        answer3 = new RadioButton();
        answer1.setToggleGroup(group);
        answer2.setToggleGroup(group);
        answer3.setToggleGroup(group);
        submit = new Button("Submit");
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void render(Question question) {
        this.title.setText("Question " + (this.client.getCurrentQuestion() + 1) + " / " + (this.client.getQuestionAmount() + 1));
        this.ask.setText(question.getQuestion());
        this.answer1.setText(question.getAnswerA());
        this.answer2.setText(question.getAnswerB());
        this.answer3.setText(question.getAnswerC());
        this.submit.setOnAction(e -> {

            this.client.sendResponse();
            this.submitCallback.run();
        });
    }

}
