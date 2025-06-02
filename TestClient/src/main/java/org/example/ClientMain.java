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

    private final EntryView entryView = new EntryView();
    private WelcomeView welcomeView;

    public void renderScoring(){
        Pair<Integer, Integer> scoring = client.getScoring();
        ScoringView scoringView = new ScoringView(scoring.getKey(), scoring.getValue());
        scoringView.setBackToWelcome(() -> {
            mainScene.setRoot(welcomeView);
        });
        mainScene.setRoot(scoringView);
    }

    public void renderQuestionView() {
        QuestionView questionView = new QuestionView(
                client.getNextQuestion(),
                client.getCurrentQuestionNumber() + 1,
                client.getQuestionAmount()
        );

        questionView.setResponseCallback(response -> {
            client.sendResponse(response);
            if(client.getCurrentQuestionNumber() < client.getQuestionAmount()) renderQuestionView();
            else renderScoring();
        });
        mainScene.setRoot(questionView);
    }


    public void conductTest() {
        client.createTest();
        renderQuestionView();
    }

    public void renderRegisterView(){
        RegisterView registerView = new RegisterView(client);
        registerView.setOnBackPressed(() -> mainScene.setRoot(entryView));
        mainScene.setRoot(registerView);
    }

    public void renderStartTestView(){
        StartTestView startTestView = new StartTestView();
        startTestView.setYesButtonCallback(this::conductTest);
        startTestView.setNoButtonCallback(() -> mainScene.setRoot(welcomeView));
        mainScene.setRoot(startTestView);
    }

    public void renderWelcomeView(){
        welcomeView = new WelcomeView(client.getUser().getFirstName(), client.getUser().getSurname());
        welcomeView.setLogout(() -> {
            client.logoutUser();
            mainScene.setRoot(entryView);
        });
        welcomeView.setStartTest(this::renderStartTestView);
        mainScene.setRoot(welcomeView);
    }

    public void renderLoginView(){
        LoginView loginView = new LoginView(client);
        loginView.setOnBackPressed(() -> mainScene.setRoot(entryView));
        loginView.setOnLoginSuccess(this::renderWelcomeView);
        mainScene.setRoot(loginView);
    }

    @Override
    public void start(Stage primaryStage) {

        entryView.setOnLogin(this::renderLoginView);
        entryView.setOnRegister(this::renderRegisterView);
        mainScene = new Scene(entryView, 500, 500);

        primaryStage.resizableProperty().setValue(Boolean.FALSE);
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

        } catch (java.rmi.ConnectException e) {
            System.out.printf("Cannot connect to server. Please check if it is running on localhost:1099\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}