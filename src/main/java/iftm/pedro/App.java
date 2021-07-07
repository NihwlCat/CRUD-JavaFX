package iftm.pedro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene principal;

    public static Scene getPrincipal(){
        return principal;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));

        ScrollPane root = loader.load();
        principal = new Scene(root);

        stage.setScene(principal);
        stage.setTitle("PROVA FINAL - POOV");
        stage.show();
    }

    public static void main(String... args) {
        System.out.println("LIGADO");
        launch();
    }
}