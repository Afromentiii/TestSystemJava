package org.example.frontend;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResultsView extends VBox {
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

    public void setBackToWelcome(Runnable callback) {
        this.backToWelcome = callback;
    }

    public ResultsView(List<Result> results) {
        this.setSpacing(30);
        this.setPadding(new Insets(30));

        MyLabel resultLabel = new MyLabel("Your tests' results");
        MyButton backButton = new MyButton("Back to Welcome");
        backButton.setOnAction(e -> {
            this.backToWelcome.run();
        });

        TableColumn<Result, Integer> nameCol = new TableColumn<>("ID");
        nameCol.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().getTestId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SS");
        TableColumn<Result, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().getSentAt().format(formatter)));

        TableColumn<Result, Integer> questionCount = new TableColumn<>("Length");
        questionCount.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().getQuestionCount()));

        TableColumn<Result, Integer> answerCount = new TableColumn<>("Correct Answers");
        answerCount.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().getCorrectAnswerCount()));

        TableColumn<Result, String> correctnessPercentage = new TableColumn<>("Score");
        correctnessPercentage.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(String.valueOf(data.getValue().getCorrectnessPercentage()) + "%"));

        TableView<Result> tableView = new TableView<>();
        tableView.getColumns().addAll(nameCol, timeCol, questionCount, answerCount, correctnessPercentage);

        ObservableList<Result> data = FXCollections.observableArrayList();
        for (Result result : results) data.add(result);

        tableView.setItems(data);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        getChildren().addAll(resultLabel, tableView, backButton);
    }
}
