package iftm.pedro.controllers;

import iftm.pedro.entities.Glasses;
import iftm.pedro.services.Service;
import iftm.pedro.utils.ChangeListener;
import iftm.pedro.utils.Utils;
import iftm.pedro.utils.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class GlassesFormController implements Initializable {

    private Glasses glasses;

    private Service service;

    public void setGlasses(Glasses glasses) {
        this.glasses = glasses;
    }

    public void setService(Service service) {
        this.service = service;
    }

    private List<ChangeListener> list = new ArrayList<>();

    public void subscribeListener(ChangeListener listener){
        list.add(listener);
    }

    private void notifyListeners(){
        for(ChangeListener listener : list){
            listener.onChange();
        }
    }


    // TextFields

    @FXML
    private TextField codTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextArea prescriptionTextField;

    // Labels

    @FXML
    private Label isImageValid;

    @FXML
    private Label isPrescriptionValid;

    @FXML
    private Label codLabel;

    public void onSaveAction(ActionEvent event){
        if(service == null)
            throw new IllegalStateException("Service not found");

        try {
            if(glasses.getId() == null){
                getFormData();
                service.saveObject(glasses);
            } else {
                getFormData();
                service.updateObject(glasses,glasses.getId());
            }
            notifyListeners();
            Utils.getOwnerStage(event).close();

        } catch (ValidationException e){
            showErrorsMessage(e.getErrors());
        }
    }

    public void hideCamps(){
        codTextField.setVisible(false);
        codLabel.setVisible(false);
    }

    public void onCancelAction(ActionEvent event){
        Utils.getOwnerStage(event).close();
    }

    public void showErrorsMessage(Map<String,String> errors){
        Set<String> keys =  errors.keySet();

        isImageValid.setText(keys.contains("prescription") ? errors.get("prescription") : "");
        isPrescriptionValid.setText(keys.contains("image") ? errors.get("image") : "");
    }

    public void getFormData(){
        ValidationException exception = new ValidationException("Validation error");

        if(prescriptionTextField.getText() == null)
            exception.addErrors("prescription","* Preencha o campo");

        if(imageTextField.getText() == null)
            exception.addErrors("image","* Preencha o campo");

        glasses.setImageLink(imageTextField.getText());
        glasses.setPrescription(prescriptionTextField.getText());

        if(!exception.getErrors().isEmpty())
            throw exception;
    }

    public void updateFormData(){
        codTextField.setText(glasses.getId());
        imageTextField.setText(glasses.getImageLink());
        prescriptionTextField.setText(glasses.getPrescription());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codTextField.setDisable(true);
    }
}
