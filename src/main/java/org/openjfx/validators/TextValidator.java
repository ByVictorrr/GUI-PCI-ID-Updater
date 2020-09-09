package org.openjfx.validators;

import com.jfoenix.controls.JFXTextField;

import java.awt.*;

public class TextValidator {
    public static boolean isEmpty(JFXTextField txtF){
        if (txtF.getText() == null || txtF.getText().trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
