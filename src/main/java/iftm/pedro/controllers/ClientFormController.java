package iftm.pedro.controllers;

import iftm.pedro.entities.Client;
import iftm.pedro.services.Service;
import iftm.pedro.utils.ChangeListener;
import iftm.pedro.utils.Utils;
import iftm.pedro.utils.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ClientFormController implements Initializable {
    private Client client;

    private Service service;

    public void setClient(Client client) {
        this.client = client;
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
    private TextField cpfTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField instagramTextField;

    // Labels

    @FXML
    private Label isValidCpf;

    @FXML
    private Label isValidName;

    @FXML
    private Label isValidEmail;

    public void onSaveAction(ActionEvent event){
        if(service == null)
            throw new IllegalStateException("Service not found");

        try {
            if(client.getClientCpf() == null){
                getFormData();
                service.saveObject(client);
            } else {
                getFormData();
                service.updateObject(client,client.getClientCpf());
            }
            notifyListeners();
            Utils.getOwnerStage(event).close();

        } catch (ValidationException e){
            showErrorsMessage(e.getErrors());
        }
    }

    public void setUneditableCamps(){
        cpfTextField.setDisable(true);
    }

    public void onCancelAction(ActionEvent event){
        Utils.getOwnerStage(event).close();
    }

    public void showErrorsMessage(Map<String,String> errors){
        Set<String> keys =  errors.keySet();

        isValidCpf.setText(keys.contains("cpf") ? errors.get("cpf") : "");
        isValidEmail.setText(keys.contains("email") ? errors.get("email") : "");
        isValidName.setText(keys.contains("name") ? errors.get("name") : "");
    }

    public void getFormData(){
        ValidationException exception = new ValidationException("Validation error");

        List<String> cpfs = service.getClients().stream().map(Client::getClientCpf).collect(Collectors.toList());

        if(cpfs.contains(cpfTextField.getText()) && !cpfTextField.isDisable()){
            exception.addErrors("cpf","* CPF já existe no banco");
            cpfTextField.clear();
        }

        if(cpfTextField.getText() == null)
            exception.addErrors("cpf","* Preencha o campo");

        if(nameTextField.getText() == null)
            exception.addErrors("name","* Preencha o campo");

        if(emailTextField.getText() == null)
            exception.addErrors("email","* Preencha o campo");

        if(!exception.getErrors().isEmpty())
            throw exception;

        client.setClientCpf(cpfTextField.getText());
        client.setName(nameTextField.getText());
        client.setEmail(emailTextField.getText());
        client.setAddress(addressTextField.getText());
        client.setPhone(phoneTextField.getText());
        client.setInstagram(instagramTextField.getText());

    }

    public void updateFormData(){
        cpfTextField.setText(client.getClientCpf());
        nameTextField.setText(client.getName());
        emailTextField.setText(client.getEmail());
        addressTextField.setText(client.getAddress());
        phoneTextField.setText(client.getPhone());
        instagramTextField.setText(client.getPhone());
    }

    public void initializeNodes(){
        Utils.setTextFieldInt(cpfTextField);
        Utils.setTextFieldLength(cpfTextField,11);

        Utils.setPhoneConstraint(phoneTextField);
        Utils.setTextFieldLength(phoneTextField,15);

        Utils.setTextFieldLength(emailTextField,45);
        Utils.setTextFieldLength(nameTextField,30);
        Utils.setTextFieldLength(addressTextField,45);
        Utils.setTextFieldLength(instagramTextField,30);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }
}
