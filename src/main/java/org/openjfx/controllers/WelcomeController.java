package org.openjfx.controllers;



import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.openjfx.utilities.FTPInputStream;
import org.openjfx.utilities.URLUtils;

import java.io.File;
import java.io.IOException;

public class WelcomeController {
    @FXML
    private Button loginButton;
    private JFXTextField password, username;
    private FTPInputStream FTPInputStream;


    public void clickedLogin(ActionEvent e){
        // Step 1 - Check to see if the password / username is correct
        // 1.1 - build the url
        // 1.2 - try to connect using this url
        /* if connected then read the pci.ids as a stream, else throw error (incorrected credentials)*/
        try {
            this.FTPInputStream = new FTPInputStream();
        }catch (Exception ex){
           ex.printStackTrace();
        }


        // Step 3 - with the file object change layouts
        Stage main = (Stage)((Node)e.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/input.fxml"));
            Parent root = loader.load();
            InputController inputController = loader.getController();
            inputController.setPciID(selectedFile);
            Scene input = new Scene(root);
            main.setScene(input);
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    /**
     * // TODO : move to another class
     * @param file - input file
     * @return true if of the form of a pci.id file, else false
     */
    private boolean validate(File file){
        return true;
    }
}
