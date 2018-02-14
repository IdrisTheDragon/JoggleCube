/*
   * @(#) LoadGridGUIController.java 1.0 2018/02/04
   *
   * Copyright (c) 2018 University of Wales, Aberystwyth.
   * All rights reserved.
   *
   */

package cs221.GP01.java.ui.controllers;

import cs221.GP01.java.ui.Mediator;
import cs221.GP01.java.ui.ScreenType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * LoadGridController - A class that controls the LoadGrid scene that is defined in Load.fxml
 * <p>
 * Used with Load.fxml
 *
 *
 * todo add a recents list
 *
 * @author Nathan Williams (naw21)
 * @version 0.2  DRAFT
 */
public class LoadGridController implements Initializable{

    /**
     * List to store recently played cubes
     */
    @FXML
    private ListView<String> listViewRecents;


    /**
     * File to load the grid from
     */
    private File gridFile = null;

    /**
     * Get recently played cubes from backend
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Load in from mediator
        listViewRecents.setItems(mediator.getRecentGrids());
    }


    /**
     * the parent VBox in this scene.
     */
    @FXML
    Node root;

    /**
     * An instance of the mediator object to interface with backend
     */
    private Mediator mediator;

    /**
     * Constructor to ensure mediator object is passed
     * @param mediator
     */
    public LoadGridController(Mediator mediator){
        this.mediator = mediator;
    }


    /**
     * When the Start Grid button is clicked it will load the Game scene.
     *
     * @see GameController
     * @throws IOException if the Game.fxml is not found.
     */
    @FXML
    void btnStartGridClicked() throws IOException {
        mediator.getScreenController().show(ScreenType.GAME);

        // Backend Example
        mediator.getHandleInput().startGame();
    }

    /**
     * When the back button is clicked it will load the Start scene.
     *
     * @see StartController
     * @throws IOException if the Start.fxml is not found.
     */
    @FXML
    void btnBackClicked() throws IOException {
        mediator.getScreenController().show(ScreenType.START);

        // Backend Example
        mediator.getHandleInput().loadMenu();
    }

    /**
     * When the Pick File button is clicked it opens a fileChooser.
     *
     */
    @FXML
    void btnPickFileClicked() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        gridFile = fileChooser.showOpenDialog(stage);
        if (gridFile != null) {
            //todo do more checks, check a valid gridFile etc
        } else {
            //todo add try again pop-up
        }
    }

    /**
     * Handle when a user clicks an option from the selection
     */
    @FXML
    public void handleMouseClick() {
        gridFile = new File(listViewRecents.getSelectionModel().getSelectedItem());
        if (gridFile != null) {
            //todo do more checks, check a valid gridFile etc
        } else {
            //todo add try again pop-up
        }
    }

    /**
     * Get the root node of the FXML
     * @return root - the root node
     */
    public Node getRoot(){
        return root;
    }
}