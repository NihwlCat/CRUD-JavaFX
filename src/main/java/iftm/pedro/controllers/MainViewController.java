package iftm.pedro.controllers;

import iftm.pedro.App;
import iftm.pedro.services.Service;
import iftm.pedro.utils.PDFExport;
import iftm.pedro.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class MainViewController {

    public void onButton1Action(){
        loadView("../LogView.fxml",(LogViewController controller) -> {
            controller.setService(new Service());
            controller.updateClientTable();
            controller.updateGlassesTable();
            controller.updateOrderTable();
        },"REGISTROS","../style/LogView.css");
    }

    public void onButton2Action(ActionEvent event) {
        String path = getPathChosen(Utils.getOwnerStage(event));
        if (!path.equals("")) {
            PDFExport export = new PDFExport(path, new Service());
            export.exportDocument();
        }
    }

    private synchronized <T> void loadView (String path, Consumer<T> consumer, String title, String stylePath){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

        try {
            VBox newBox = loader.load();

            Scene mainScene = App.getPrincipal();
            mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(stylePath)).toExternalForm());

            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            VBox menuPane = (VBox) mainVbox.getChildren().get(0);
            ((Label) ((VBox) menuPane.getChildren().get(1)).getChildren().get(0)).setText(title);
            mainVbox.getChildren().remove(1);
            mainVbox.getChildren().addAll(newBox.getChildren());


            T controller = loader.getController();
            consumer.accept(controller);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String getPathChosen(Stage stage){
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("SELECIONE A PASTA DE DESTINO");
        dc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File f = dc.showDialog(stage);

        return (f != null) ? f.getAbsolutePath() : "";
    }
}
