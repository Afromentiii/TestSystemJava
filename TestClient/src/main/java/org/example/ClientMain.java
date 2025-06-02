package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.example.frontend.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain extends Application {
    private static Client client;
    private static Scene mainScene;

    public void renderScoring(){
        Pair<Integer, Integer> scoring = client.getScoring();
        ScoringView scoringView = new ScoringView(scoring.getKey(), scoring.getValue());
        scoringView.setBackToWelcome(() -> {

        });
        mainScene.setRoot(scoringView);
        return;
    }

    public void renderQuestion() {
        QuestionView questionView = new QuestionView(
                client.getNextQuestion(),
                client.getCurrentQuestionNumber(),
                client.getQuestionAmount()
        );

        questionView.setResponseCallback(response -> {
            client.sendResponse(response); // prawdopodobnie inkrementuje wewnętrzny licznik pytania
            renderQuestion();              // wywołaj kolejne pytanie
        });

        if(client.getCurrentQuestionNumber() + 1 >= client.getQuestionAmount()) {
            questionView.setResponseCallback(e -> {
                renderScoring();
                return;
            });
        }

        mainScene.setRoot(questionView);
    }


    public void conductTest(Stage primaryStage) {
        client.createTest();
        renderQuestion();
    }



    @Override
    public void start(Stage primaryStage) {

        //Views
        LoginView loginView = new LoginView(primaryStage, client);
        RegisterView registerView = new RegisterView(primaryStage, client);
        EntryView entryView = new EntryView(primaryStage, client);
        WelcomeView welcomeView = new WelcomeView(primaryStage);
        StartTestView startTestView = new StartTestView(primaryStage);

        //Scenes
        mainScene = new Scene(entryView, 500, 500);

        // Callbacks
        entryView.setOnLogin(() -> {
            loginView.refresh();
            mainScene.setRoot(loginView);
        });
        entryView.setOnRegister(() -> {
            registerView.refresh();
            mainScene.setRoot(registerView);
        });

        loginView.setOnLoginSuccess(() -> {
            welcomeView.refresh(client);
            mainScene.setRoot(welcomeView);
        });
        loginView.setOnBackPressed(() -> mainScene.setRoot(entryView));

        registerView.setOnBackPressed(() -> mainScene.setRoot(entryView));

        welcomeView.setLogout(() -> mainScene.setRoot(entryView));
        welcomeView.setStartTest(() -> mainScene.setRoot(startTestView));

        startTestView.setYesButtonCallback(() -> conductTest(primaryStage));
        startTestView.setNoButtonCallback(() -> mainScene.setRoot(welcomeView));


        primaryStage.setTitle("RMI Testing App");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            InterfaceRMI server = (InterfaceRMI) registry.lookup("Testy");
            client = new Client(null, server);

            launch(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}