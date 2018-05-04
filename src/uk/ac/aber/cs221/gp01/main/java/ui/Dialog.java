/*
 * @(#) Dialog.java 1.1 2018/04/30
 *
 * Copyright (c) 2018 University of Wales, Aberystwyth.
 * All rights reserved.
 *
 */

package uk.ac.aber.cs221.gp01.main.java.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 * IDialog - A central class to create and control and dialog popups used in the system
 *
 * @author Rhys Evans (rhe24)
 * @version 1.1
 * @see IDialog
 */
public class Dialog implements IDialog {

    private TextInputDialog textInputDialog;
    private Alert confirmationDialog;
    private Alert informationDialog;

    /**
     * Create a text input dialog to prompt the user for information
     *
     * @param headerText
     * @param contentText
     * @param defaultValue
     * @param graphic
     * @param allowCancel
     * @return the text inputted by the user
     */
    public String showInputDialog(String headerText, String contentText, String defaultValue, ImageView graphic, boolean allowCancel) {
        textInputDialog = new TextInputDialog(defaultValue);
        textInputDialog.setHeaderText(headerText);
        textInputDialog.setContentText(contentText);
        textInputDialog.setGraphic(graphic);
        textInputDialog.initStyle(StageStyle.UNDECORATED);
        textInputDialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(allowCancel);

        // Store the user input as string and Optional
        Optional<String> rawInput;
        String input;

        // Allow things to be ran on first iteration of do-while
        boolean firstAttempt = true;

        // Prompt user for input until input is provided
        do {

            if (!firstAttempt) {
                textInputDialog.setHeaderText("Invalid Entry, Please try again");
            }

            rawInput = textInputDialog.showAndWait();
            input = rawInput.orElse("");

            firstAttempt = false;
        } while (!rawInput.equals(Optional.empty()) && !isValidInput(input));

        return input;
    }

    /**
     * Create a confirmation dialog to prompt the user for confirmation for a given action
     *
     * @param title
     * @param contentText
     * @return the user's choice
     */
    public Optional<ButtonType> showConfirmationDialog(String title, String contentText) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setTitle(title);
        confirmationDialog.setContentText(contentText);

        return confirmationDialog.showAndWait();
    }

    /**
     * Create a information dialog to notify the user
     *
     * @param title
     * @param contentText
     */
    public void showInformationDialog(String title, String contentText) {
        informationDialog = new Alert(Alert.AlertType.INFORMATION);
        informationDialog.setHeaderText(null);
        informationDialog.setTitle(title);
        informationDialog.setContentText(contentText);

        informationDialog.showAndWait();
    }

    /**
     * Verify that user input is correct
     *
     * @param input string to be validated
     * @return true if string is valid, else false
     */
    public boolean isValidInput(String input) {
        return input.matches("^[a-zA-Z0-9_]*$") && (input.length() > 1) && (input.length() < 20);
    }

    /**
     * Gets text iiput dialog
     *
     * @return textImputDialog
     */
    public TextInputDialog getTextInputDialog() {
        return textInputDialog;
    }

    /**
     * Get the confirmation dialog object
     * @return confirmationDialog
     */
    public Alert getConfirmationDialog() {
        return confirmationDialog;
    }

    /**
     * Get the information dialog object
     * @return informationDialog
     */
    public Alert getInformationDialog() {
        return informationDialog;
    }
}
