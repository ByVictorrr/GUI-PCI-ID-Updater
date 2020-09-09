package org.openjfx.controllers;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.boxes.Display;
import org.openjfx.models.Device;
import org.openjfx.models.PCI_ID;
import org.openjfx.parsers.IDParser;
import org.openjfx.utilities.FTPInputStream;
import org.openjfx.validators.TextValidator;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.ResourceBundle;


public class InputController implements Initializable {
    private File pciID;
    @FXML
    Button view, add, cancel, submitChanges;
    @FXML
    JFXTextField vID, dID, svID, sdID, desc;
    // TODO: need a ref to the file from welcomeController
    private PriorityQueue<Device> devices;
    private ViewController viewController;
    private FTPInputStream orgPciFile;

    private static final int REGEX = 0, REQUIRED = 1;

    public File getPciID() {
        return pciID;
    }

    public void setPciID(File pciID) {
        this.pciID = pciID;
    }

    void setValidators(JFXTextField textField){
       RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
       textField.getValidators().add(requiredFieldValidator);
       requiredFieldValidator.setMessage("No Input Given");
    }
    void setIDValidators(JFXTextField textField){
        RegexValidator regexValidator = new RegexValidator();
        textField.getValidators().add(regexValidator);
        regexValidator.setRegexPattern("[0-9a-fA-F]{4,4}");
        regexValidator.setMessage("Enter in four digit hexadecimal number");
        setValidators(textField);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Step 1- set up validators
        setIDValidators(vID);
        setIDValidators(dID);
        setIDValidators(svID);
        setIDValidators(sdID);
        setValidators(desc);

        vID.focusedProperty().addListener((obs, o, n)-> {
            if(!n){
                vID.getValidators().get(REQUIRED).validate();
            }else{
                vID.getValidators().get(REGEX).validate();
            }

        });

        dID.focusedProperty().addListener((obs, o, n)-> {
            if(!n){
                dID.getValidators().get(REQUIRED).validate();
            }else{
                dID.getValidators().get(REGEX).validate();
            }

        });

        svID.focusedProperty().addListener((obs, o, n)-> {
            if(!n){
                svID.getValidators().get(REQUIRED).validate();
            }else{
                svID.getValidators().get(REGEX).validate();
            }

        });
        sdID.focusedProperty().addListener((obs, o, n)-> {
            if(!n){
                sdID.getValidators().get(REQUIRED).validate();
            }else{
                sdID.getValidators().get(REGEX).validate();
            }

        });
        desc.focusedProperty().addListener((obs, o, n)-> {
            if (!n) {
                desc.getValidators().get(REQUIRED).validate();
            }
        });

        this.devices=new PriorityQueue<>();
        try {
            this.orgPciFile = new FTPInputStream("/dev/pci.ids");
            if(this.orgPciFile.getInputStream().markSupported()){
                this.orgPciFile.getInputStream().mark(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void clickedViewButton(ActionEvent e){
        Stage window = new Stage();
        // block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Device View");
        window.setMinWidth(250);

        try {
            FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/view.fxml"));
            Parent root = viewLoader.load();
            Scene viewScene = new Scene(root);
            ViewController.setDeviceList(this.devices);
            window.setScene(viewScene);
            window.show();
        }catch (Exception a){
            a.printStackTrace();
        }
    }
    @FXML
    public void clickedAddButton(ActionEvent e){
       Long  vid, did, svid, sdid;
       String de = desc.getText();
       Device d;
       IDParser.DeviceLineNumber isSet ;
       // Step 1 - validate the inputs (are they empty or not)
        if (TextValidator.isEmpty(vID) || TextValidator.isEmpty(dID) || TextValidator.isEmpty(svID)
                || TextValidator.isEmpty(sdID) || TextValidator.isEmpty(desc)){
            Display.displayAlertBox("One of your fields is empty");
        }
        try {
            vid = Long.parseLong(vID.getText(), 16);
            did = Long.parseLong(dID.getText(), 16);
            svid = Long.parseLong(svID.getText(), 16);
            sdid = Long.parseLong(sdID.getText(), 16);
            PCI_ID id = new PCI_ID(vid, did, svid, sdid);
            d = new Device(de, id);
            if ((isSet = IDParser.setLineNumber(d, this.orgPciFile)) == IDParser.DeviceLineNumber.EXISTS) {
                Display.displayAlertBox("This device already exists");
            } else if (isSet == IDParser.DeviceLineNumber.NO_DEV) {
                Display.displayAlertBox("No Device ID associated with entered data was found");
            } else if (isSet == IDParser.DeviceLineNumber.NO_VEN){
                Display.displayAlertBox("No Vendor ID associated with entered data was found");
            }else{
                this.devices.add(d);
            }


        }
        catch (IOException i) {
            Display.displayAlertBox("IO error");

        }



    }

    @FXML
    public void clickedCancelButton(ActionEvent e){
        // go back to the last scene
    }
    @FXML
    public void clickedClearButton(ActionEvent e){
        devices = null;
    }
    @FXML
    public void clickedSubmitButton(ActionEvent e){


    }


}
