package iftm.pedro.controllers;

import iftm.pedro.entities.Client;
import iftm.pedro.entities.Glasses;
import iftm.pedro.entities.Order;
import iftm.pedro.services.Service;
import iftm.pedro.utils.ChangeListener;
import iftm.pedro.utils.Utils;
import iftm.pedro.utils.ValidationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class OrderFormController implements Initializable {
    private Order order;

    private Service service;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setService(Service service) {
        this.service = service;
    }

    private final List<ChangeListener>  list = new ArrayList<>();

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
    private DatePicker datePicker;

    @FXML
    private TextField discountTextField;

    @FXML
    private TextField priceTextField;

    // ComboBoxes

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private ComboBox<Glasses> glassesComboBox;

    // Labels

    @FXML
    private Label codLabel;

    @FXML
    private Label isPriceValid;

    @FXML
    private Label isClientValid;

    @FXML
    private Label isGlassValid;

    @FXML
    private Label isDateValid;

    public void hideCamps(){
        codTextField.setVisible(false);
        codLabel.setVisible(false);
    }

    public void fillComboBox(){
        List<Client> clients = service.getClientsToBox();

        if(clients.isEmpty())
            throw new ValidationException("Empyt list issue");

        clientComboBox.setItems(FXCollections.observableList(service.getClientsToBox()));
        glassesComboBox.setItems(FXCollections.observableList(service.getGlasses()));
    }

    public void onSaveAction(ActionEvent event){
        if(service == null)
            throw new IllegalStateException("Service not found");

        try {
            if(order.getCod() == null){
                getFormData();
                service.saveObject(order);
            } else {
                getFormData();
                service.updateObject(order,order.getCod());
            }

            notifyListeners();
            Utils.getOwnerStage(event).close();

        } catch (ValidationException e){
            showErrorsMessage(e.getErrors());
        }
    }

    public void showErrorsMessage(Map<String,String> errors){
        Set<String> keys =  errors.keySet();

        isPriceValid.setText(keys.contains("price") ? errors.get("price") : "");
        isDateValid.setText(keys.contains("date") ? errors.get("date") : "");
        isClientValid.setText(keys.contains("client") ? errors.get("client") : "");
        isGlassValid.setText(keys.contains("glasses") ? errors.get("glasses") : "");
    }


    private void getFormData(){
        ValidationException exception = new ValidationException("Validation error");

        if(datePicker.getValue() == null){
            exception.addErrors("date","* Preencha o campo");
        } else {
            Instant instant = Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
            order.setOrderDate(Date.from(instant));
        }

        if(priceTextField.getText().equals("")){
            exception.addErrors("price","* Preencha o campo");
        }

        if(clientComboBox.getValue() == null){
            exception.addErrors("client","* Deve ser selecionado");
        }

        if(glassesComboBox.getValue() == null){
            exception.addErrors("glasses","* Deve ser selecionado");
        }

        if(!exception.getErrors().isEmpty())
            throw exception;

        order.setDiscount(Utils.tryParseToDouble(discountTextField.getText()));
        order.setPrice(Utils.tryParseToDouble(priceTextField.getText()));
        order.setClient(service.findObject(clientComboBox.getValue().getClientCpf()));
        order.setGlasses(service.findObject(glassesComboBox.getValue().getId()));
    }

    public void onCancelAction(ActionEvent event){
        Utils.getOwnerStage(event).close();
    }


    private void initializeComboBox(){
        Callback<ListView<Client>, ListCell<Client>> clientFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getClientCpf() + " - " + item.getName());
            }
        };

        Callback<ListView<Glasses>, ListCell<Glasses>> glassesFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Glasses item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getId());
            }
        };

        clientComboBox.setCellFactory(clientFactory);
        glassesComboBox.setCellFactory(glassesFactory);

        clientComboBox.setButtonCell(clientFactory.call(null));
        glassesComboBox.setButtonCell(glassesFactory.call(null));

        clientComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client client) {
                return client.getClientCpf() + " - " + client.getName();
            }

            @Override
            public Client fromString(String s) {
                return null;
            }
        });

        glassesComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Glasses glasses) {
                return glasses.getId();
            }

            @Override
            public Glasses fromString(String s) {
                return null;
            }
        });
    }

    public void updateFormData(){
        codTextField.setText(order.getCod());
        discountTextField.setText(order.getDiscount() == 0.0 ? "" : String.valueOf(order.getDiscount()));
        priceTextField.setText(order.getPrice() == 0.0 ? "" : String.valueOf(order.getPrice()));

        if(order.getClient() == null)
            clientComboBox.getSelectionModel().selectFirst();
        else {
            clientComboBox.getSelectionModel().select(order.getClient());
            clientComboBox.setDisable(true);
        }


        if(order.getGlasses() == null)
            glassesComboBox.getSelectionModel().selectFirst();
        else
            glassesComboBox.getSelectionModel().select(order.getGlasses());

        if(order.getOrderDate() != null)
            datePicker.setValue(LocalDate.from(LocalDateTime.ofInstant(order.getOrderDate().toInstant(),ZoneId.systemDefault())));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Utils.setTextFieldDouble(priceTextField);
        Utils.setTextFieldDouble(discountTextField);
        codTextField.setDisable(true);
        initializeComboBox();
        Utils.formatDatePicker(datePicker,"dd/MM/yyyy");
    }
}
