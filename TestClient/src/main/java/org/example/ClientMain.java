package org.example;

import org.example.frontend.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {
    private static Client client;
    private static Scene welcome;
    private static Scene question;

    @Override
    public void start(Stage primaryStage) {

        //Views
        LoginView loginView = new LoginView(primaryStage, client);
        RegisterView registerView = new RegisterView(primaryStage, client);
        EntryView entryView = new EntryView(primaryStage, client);
        WelcomeView welcomeView = new WelcomeView(primaryStage);
        StartTestView startTestView = new StartTestView(primaryStage);
        QuestionView questionView = new QuestionView(primaryStage);


        //Scenes
        Scene login = new Scene(loginView, 500, 300);
        Scene register = new Scene(registerView, 500, 300);
        Scene entry = new Scene(entryView, 500, 300);
        welcome = new Scene(welcomeView, 500, 300);
        Scene startTest = new Scene(startTestView, 500, 300);


        // Callbacks
        entryView.setOnLogin(() -> primaryStage.setScene(login));
        entryView.setOnRegister(() -> primaryStage.setScene(register));

        loginView.setOnLoginSuccess(() -> {
            welcomeView.refresh(client);
            primaryStage.setScene(welcome);
        });

        loginView.setOnBackPressed(() -> primaryStage.setScene(entry));
        registerView.setOnBackPressed(() -> primaryStage.setScene(entry));
        welcomeView.setLogout(() -> primaryStage.setScene(entry));
        welcomeView.setStartTest(() -> primaryStage.setScene(startTest));
        startTestView.setYesButtonCallback(() -> {

        });
        startTestView.setNoButtonCallback(() -> {

        });



        primaryStage.setTitle("RMI Testing App");
        primaryStage.setScene(entry);
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