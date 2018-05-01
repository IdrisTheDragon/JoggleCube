/*
   * @(#) LoadGridGUIController.java 1.0 2018/02/04
   *
   * Copyright (c) 2018 University of Wales, Aberystwyth.
   * All rights reserved.
   *
   */

package cs221.GP01.main.java.ui.controllers;

import cs221.GP01.main.java.model.JoggleCube;
import cs221.GP01.main.java.ui.Navigation;
import cs221.GP01.main.java.ui.ScreenType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

/**
 * LoadGrid - A class that controls the LoadGrid scene that is defined in Load.fxml
 * <p>
 * Used with Load.fxml
 *
 *
 * todo add a recents list
 *
 * @author Nathan Williams (naw21)
 * @version 0.2  DRAFT
 */
public class LoadGrid extends BaseScreen implements INeedPrep {


    private static LoadGrid loadGridView;

    private LoadGrid(){}

    public static LoadGrid getInstance(){
        if(loadGridView == null){
            synchronized (LoadGrid.class){
                if(loadGridView == null){
                    loadGridView = new LoadGrid();
                }
            }
        }
        return loadGridView;
    }

    /**
     * List to store recently played cubes
     */
    @FXML
    private ListView<String> listViewRecents;


    /**
     * File to load the grid from
     */
    private String fileName = null;

    public String getFileName() {
        return fileName;
    }

    /**
     * Get recently played cubes from backend
     */
    public void prepView(){
        listViewRecents.setItems(JoggleCube.getInstance().getRecentGrids());
    }



    /**
     * When the Start Grid button is clicked it will load the Game scene.
     *
     * @see GameView
     */
    @FXML
    public void btnStartGridClicked() {
        if(JoggleCube.getInstance().loadGrid(fileName)){
            Navigation.getInstance().switchScreen(ScreenType.GAME);
        } else {
            //todo file not loaded message
        }


    }

    /**
     * When the back button is clicked it will load the Start scene.
     *
     * @see Start
     */
    @FXML
    public void btnBackClicked() {
        Navigation.getInstance().switchScreen(ScreenType.START);
    }


    /**
     * Handle when a user clicks an option from the selection
     */
    @FXML
    public void handleMouseClick() {

        fileName = listViewRecents.getSelectionModel().getSelectedItem();
    }

    /**
     * Error handling method to show the user an error alert when something goes wrong with file loading
     * @param message - the error message to display on the popup
     */
    public void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Error Loading File: \n\n" + message);

        alert.showAndWait();
    }
}