package iftm.pedro.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class Utils {
    public static Stage getOwnerStage(ActionEvent event){
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public static double tryParseToDouble(String str){
        try{
            return Double.parseDouble(str);
        } catch (NumberFormatException e){
            return 0.0;
        }
    }

    public static void setTextFieldInt(TextField txt){
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null && !newValue.matches("\\d*")){
                txt.setText(oldValue);
            }
        });
    }

    public static void setTextFieldDouble(TextField txt) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null && !newValue.matches("\\d*([.]\\d*)?")) {
                txt.setText(oldValue);
            }
        });
    }

    public static void setTextFieldLength(TextField txt, int max) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > max)
                txt.setText(oldValue);
        });
    }

    public static void setPhoneConstraint(TextField txt){
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null && !newValue.matches("(\\d|\\(|\\)|-|\\s)*")){
                txt.setText(oldValue);
            }
        });
    }

    public static Optional<ButtonType> showConfirmation(AlertType type, String ... args){
        Alert alert = new Alert(type);

        alert.setTitle(args[0]);
        alert.setHeaderText(null);
        alert.setContentText(args[1]);

        return alert.showAndWait();
    }

    public static void showAlerts (AlertType type, String ... args){
        Alert alert = new Alert(type);
        alert.setTitle(args[0]);
        alert.setHeaderText(args[1]);
        alert.setContentText(args[2]);

        alert.show();
    }

    public static void formatDatePicker(DatePicker datePicker, String format) {
        datePicker.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
            {
                datePicker.setPromptText(format.toLowerCase());
            }
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
