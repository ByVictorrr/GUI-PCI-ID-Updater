package org.openjfx.boxes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Display {
    public static void displayAlertBox(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg + " ;Close " + " ?", ButtonType.CLOSE);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.out.println("enter valid inputs");
        }
    }
}
