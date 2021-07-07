package iftm.pedro.controllers;

import iftm.pedro.App;
import iftm.pedro.entities.Client;
import iftm.pedro.entities.Glasses;
import iftm.pedro.entities.Order;
import iftm.pedro.services.Service;
import iftm.pedro.utils.ChangeListener;
import iftm.pedro.utils.Utils;
import iftm.pedro.utils.ValidationException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LogViewController implements Initializable, ChangeListener {

    private Service service;
    private ObservableList<Client> obsClients;
    private ObservableList<Glasses> obsGlasses;
    private ObservableList<Order> obsOrders;

    public void setService(Service service){
        this.service = service;
    }

    // Tables

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableView<Glasses> glassesTableView;

    @FXML
    private TableView<Order> orderTableView;

    // Client Columns

    @FXML
    private TableColumn<Client,String> clientCpfColumn;

    @FXML
    private TableColumn<Client,String> clientNameColumn;

    @FXML
    private TableColumn<Client,String> clientEmailColumn;

    @FXML
    private TableColumn<Client,String> clientAddressColumn;

    @FXML
    private TableColumn<Client,String> clientPhoneColumn;

    @FXML
    private TableColumn<Client,String> clientInstagramColumn;

    // Glasses Columns

    @FXML
    private TableColumn<Glasses,String> glassesIdColumn;

    @FXML
    private TableColumn<Glasses,String> glassesImageColumn;

    @FXML
    private TableColumn<Glasses,String> glassesPrescriptionColumn;

    // Orders Columns

    @FXML
    private TableColumn<Order,String> orderIdColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order,Double> orderPriceColumn;

    @FXML
    private TableColumn<Order,Double> orderDiscountColumn;

    @FXML
    private TableColumn<Order,String> orderClientColumn;

    @FXML
    private TableColumn<Order,String> orderGlassesColumn;

    // Dynamic buttons

    @FXML
    private TableColumn<Client,Client> EDITcolumn;

    @FXML
    private TableColumn<Client,Client> REMOVEcolumn;

    @FXML
    private TableColumn<Glasses,Glasses> glassesEDITcolumn;

    @FXML
    private TableColumn<Glasses,Glasses> glassesREMOVEcolumn;

    @FXML
    private TableColumn<Order,Order> orderEDITcolumn;

    @FXML
    private TableColumn<Order,Order> orderREMOVEcolumn;

    public void onActionReturn(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainView.fxml"));
        try {
            ScrollPane pane = loader.load();
            App.getPrincipal().setRoot(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeButtons(){
        REMOVEcolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        glassesREMOVEcolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        orderREMOVEcolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        glassesREMOVEcolumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("X");

            @Override
            protected void updateItem(Glasses obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> removeObject(obj));
                button.setId("cancel-button");
            }
        });

        orderREMOVEcolumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("X");

            @Override
            protected void updateItem(Order obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> removeObject(obj));
                button.setId("cancel-button");
            }
        });

        REMOVEcolumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("X");

            @Override
            protected void updateItem(Client obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> removeObject(obj));
                button.setId("cancel-button");
            }
        });
    }

    private void editButtons() {
        EDITcolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        glassesEDITcolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        orderEDITcolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        orderEDITcolumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("EDITAR");

            @Override
            protected void updateItem(Order obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> initForm("../OrderForm.fxml", obj, Utils.getOwnerStage(event)));
                button.setId("action-button");
            }
        });


        glassesEDITcolumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("EDITAR");

            @Override
            protected void updateItem(Glasses obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> initForm("../GlassesForm.fxml", obj, Utils.getOwnerStage(event)));
                button.setId("action-button");
            }
        });

        EDITcolumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("EDITAR");

            @Override
            protected void updateItem(Client obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> initForm("../ClientForm.fxml", obj, Utils.getOwnerStage(event)));
                button.setId("action-button");
            }
        });
    }

    private <T> void removeObject(T object){
        Optional<ButtonType> res = Utils.showConfirmation(Alert.AlertType.CONFIRMATION,"Remover registro", "Confirme para remover");
        if(res.isPresent() && res.get() == ButtonType.OK){
            service.removeObject(object);
        }

        if (object.getClass() == Client.class){
            updateClientTable();
            updateOrderTable();
        } else if (object.getClass() == Glasses.class){
            updateGlassesTable();
            updateOrderTable();
        } else {
            updateOrderTable();
        }
    }


    public void onButton1Action(ActionEvent event){
        if (((Button) event.getSource()).getText().equals("CLIENTE")){
            initForm("../ClientForm.fxml",new Client(),Utils.getOwnerStage(event));
        } else if(((Button) event.getSource()).getText().equals("PRODUTO")){
            initForm("../GlassesForm.fxml",new Glasses(),Utils.getOwnerStage(event));
        } else {
            initForm("../OrderForm.fxml",new Order(),Utils.getOwnerStage(event));
        }
    }

    public synchronized  <T> void initForm(String path, T object, Stage stage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

        try {
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);

            Service s = new Service();

            if(object.getClass() == Client.class){
                ClientFormController controller = loader.getController();
                controller.setClient((Client) object);
                controller.setService(s);
                controller.subscribeListener(this);
                controller.updateFormData();

                if(((Client) object).getClientCpf() != null){
                    controller.setUneditableCamps();
                }

            } else if(object.getClass() == Glasses.class){
                GlassesFormController controller = loader.getController();
                controller.setGlasses((Glasses) object);
                controller.setService(s);
                controller.subscribeListener(this);
                controller.updateFormData();

                if(((Glasses) object).getId() == null){
                    controller.hideCamps();
                }
            } else {
                OrderFormController controller = loader.getController();
                controller.setOrder((Order) object);
                controller.setService(s);
                controller.fillComboBox();
                controller.subscribeListener(this);
                controller.updateFormData();

                if(((Order) object).getCod() == null){
                    controller.hideCamps();
                }
            }

            dialogStage.initOwner(stage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        } catch (ValidationException e){
            Utils.showAlerts(Alert.AlertType.WARNING,"", "Nenhum cliente disponível", "Todos os clientes já fizeram um pedido");
        }

    }
    public void updateClientTable(){
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        obsClients = FXCollections.observableArrayList(service.getClients());
        clientTableView.setItems(obsClients);

    }

    public void updateGlassesTable(){
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        obsGlasses= FXCollections.observableArrayList(service.getGlasses());
        glassesTableView.setItems(obsGlasses);

    }

    public void updateOrderTable(){
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        obsOrders = FXCollections.observableArrayList(service.getOrders());
        orderTableView.setItems(obsOrders);

    }


    public void initializeColumns() {
        clientCpfColumn.setCellValueFactory(new PropertyValueFactory<>("clientCpf"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clientInstagramColumn.setCellValueFactory(new PropertyValueFactory<>("instagram"));

        glassesIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        glassesImageColumn.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        glassesPrescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("prescription"));

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("cod"));
        orderDateColumn.setCellValueFactory(param -> new SimpleStringProperty(Utils.formatDate(param.getValue().getOrderDate())));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        orderClientColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClient().getName()));
        orderGlassesColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGlasses().getId()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        editButtons();
        removeButtons();
    }

    @Override
    public void onChange() {
        updateOrderTable();
        updateGlassesTable();
        updateClientTable();
    }
}
