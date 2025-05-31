package org.example;

import org.example.frontend.EntryView;
import org.example.frontend.LoginView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.frontend.RegisterView;
import org.example.frontend.WelcomeView;

public class ClientMain extends Application {
    public static Client client;

    @Override
    public void start(Stage primaryStage) {

        //Views
        WelcomeView welcomeView = new WelcomeView(primaryStage, client);
        LoginView loginView = new LoginView(primaryStage, client);
        RegisterView registerView = new RegisterView(primaryStage, client);
        EntryView entryView = new EntryView(primaryStage, client);

        //Scenes
        Scene login = new Scene(loginView, 500, 300);
        Scene register = new Scene(registerView, 500, 300);
        Scene entry = new Scene(entryView, 500, 300);
        Scene welcome = new Scene(welcomeView, 500, 300);


        // Callbacks
        entryView.setOnLogin(() -> primaryStage.setScene(login));
        entryView.setOnRegister(() -> primaryStage.setScene(register));
        loginView.setOnLoginSuccess(() -> primaryStage.setScene(welcome));
        registerView.setOnBackPressed(() -> primaryStage.setScene(entry));


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