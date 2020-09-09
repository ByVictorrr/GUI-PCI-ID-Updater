package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.openjfx.models.Device;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

// pops up another scene of all pending devices
public class ViewController implements Initializable {

    private static PriorityQueue<Device> deviceList;
    @FXML
    TableView<Device> table;
    @FXML
    TableColumn descCol, vidCol, didCol, svidCol, sdidCol;
    @FXML
    Button close, load;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // show devices if any
        descCol.setCellValueFactory(new PropertyValueFactory<Device, String>("desc"));
        // TODO: check if there is a way to call a sub variable from that object
        vidCol.setCellValueFactory(new PropertyValueFactory<Device, String>("vid"));
        didCol.setCellValueFactory(new PropertyValueFactory<Device, String>("did"));
        svidCol.setCellValueFactory(new PropertyValueFactory<Device, String>("svid"));
        sdidCol.setCellValueFactory(new PropertyValueFactory<Device, String>("sdid"));


    }

    public static void setDeviceList(PriorityQueue<Device> deviceList) {
        ViewController.deviceList = deviceList;
    }
    public void clickedCloseButton(ActionEvent e){
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.close();
    }
    public void clickedLoadButton(ActionEvent e){
        ObservableList<Device> data = FXCollections.observableList(new ArrayList<>(deviceList));
        table.setItems(data);
    }
}
