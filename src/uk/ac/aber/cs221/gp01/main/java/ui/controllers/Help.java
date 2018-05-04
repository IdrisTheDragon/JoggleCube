/*
 * @(#) HelpController.java 1.3 2018/02/24
 *
 * Copyright (c) 2018 University of Wales, Aberystwyth.
 * All rights reserved.
 *
 */

package uk.ac.aber.cs221.gp01.main.java.ui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import uk.ac.aber.cs221.gp01.main.java.ui.Navigation;
import uk.ac.aber.cs221.gp01.main.java.ui.ScreenType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the Help.fxml file to handle the displaying of various help screens
 * @author Rhys Evans (rhe24)
 * @author Nathan Williams (naw21@aber.ac.uk)
 * @author Alex Thumwood (alt38)
 * @version 1.3
 *
 */
public class Help extends BaseOverlay implements Initializable, INeedPrep {

    private static Help helpView;

    /**
     * The file path prefix to the helppages
     */
    private static final String PAGES_PATH_PREFIX = "/uk/ac/aber/cs221/gp01/main/resource/view/helppages/";

    /**
     * List to store all the help screens available as FXML parent nodes
     * so they can be injected / removed from the help screen with ease
     */
    private ArrayList<Parent> helpScreens = new ArrayList<>();

    /**
     * The currently indexed help page (used to display correct page etc)
     */
    private int currentPageIndex;

    /**
     * The FXML node of the subscene that will act as a container for the help page
     */
    @FXML
    private SubScene helpPageContainer;

    /**
     * The FXML node of the carousel indicator container
     */
    @FXML
    private HBox carouselIndicatorContainer;

    /**
     * Create all the pages as FXML parent nodes
     */
    private Help(){
        try {
            helpScreens.add(createHelpPage("Introduction.fxml"));
            helpScreens.add(createHelpPage("Rotating.fxml"));
            helpScreens.add(createHelpPage("Exploding.fxml"));
            helpScreens.add(createHelpPage("ViewToggle.fxml"));
            helpScreens.add(createHelpPage("Selecting.fxml"));
            helpScreens.add(createHelpPage("Scoring.fxml"));
            helpScreens.add(createHelpPage("CubeColouring.fxml"));
            helpScreens.add(createHelpPage("CubeColouringCB.fxml"));
            helpScreens.add(createHelpPage("ColourBlindToggle.fxml"));

        } catch (IOException e) {
            // Print error to console to inform of missing source files
            System.err.println("Error! Missing help page");
            e.printStackTrace();
        }
    }

    /**
     * Initialize the help overlay for first time use
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Create the indicators at the bottom of the screen
        createCarouselIndicators();
    }

    /**
     * Prep the overlay by loading the default page every time
     */
    @Override
    public void prepView(){
        // Set current index to 0
        currentPageIndex = 0;

        // Inject FXML of first entry in the helpScreens list to the subscene root
        changePage();
    }

    /**
     * Handles the close button of the overlay being clicked
     */
    @FXML
    public void closeBtnClicked(){
        Navigation.getInstance().hideOverlay(ScreenType.HELP, parentController);
    }

    /**
     * Handle the right navigation being pressed by increasing the current help
     * screen index by one and looping around using modulo and updating currently displayed
     * page
     */
    @FXML
    public void btnRightNavClicked(){
        // Increment the page index
        currentPageIndex++;

        // Make sure the value is within the bounds of the array (if not loop around)
        currentPageIndex %= helpScreens.size();

        // Update the page to display the correct screen
        changePage();
    }

    /**
     * Handle the right navigation being pressed by decreasing the current help
     * screen index by one and looping around using modulo and updating currently displayed
     * page
     */
    @FXML
    public void btnLeftNavClicked(){
        currentPageIndex--;

        // Make sure the value is within the bounds of the array (if not loop around)
        if(currentPageIndex < 0) currentPageIndex = helpScreens.size() - 1;

        // Update the page to display the correct screen
        changePage();
    }


    /**
     * Utility function to create the carouselIndicator icons depending on size of the list
     */
    @FXML
    private void createCarouselIndicators(){

        // Iteratively create a carouselindicator for each help screen
        for(int i = 0; i < helpScreens.size(); i++){
            Button newButton = new Button();

            newButton.getStyleClass().add("carouselIndicator");
            newButton.setMinSize(12, 12);
            newButton.setMaxSize(12, 12);
            newButton.setPrefSize(12, 12);

            // add newly created button to the hbox
            carouselIndicatorContainer.getChildren().add(newButton);

            // Add button behaviour to the carousel indicator
            newButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Change the index of the page to the index of the indicator
                    currentPageIndex = carouselIndicatorContainer.getChildren().indexOf(newButton);
                    changePage();
                }
            });
        }
    }

    /**
     * Utility function to create the help pages as an FXMLLoader
     * @param name - the name of the FXML file
     * @return loader - the created FXML parent node
     */
    private Parent createHelpPage(String name) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PAGES_PATH_PREFIX + name));

        return loader.load();
    }

    /**
     * Utility function to change the page that is currently displayed
     */
    private void changePage(){

        // Set the FXML root
        helpPageContainer.setRoot(helpScreens.get(currentPageIndex));

        // De-select other carousel indicators
        for(int i = 0; i < carouselIndicatorContainer.getChildren().size(); i++){
            carouselIndicatorContainer.getChildren().get(i).getStyleClass().remove("carouselIndicator-selected");
        }

        // Update the carousel indicator
        carouselIndicatorContainer.getChildren().get(currentPageIndex).getStyleClass().add("carouselIndicator-selected");
    }

    /**
     * Getting instance of helpView
     *
     * @return returns the instance of this class object.
     */
    public static Help getInstance(){
        if(helpView == null){
            synchronized (Help.class){
                if(helpView == null){
                    helpView = new Help();
                }
            }
        }
        return helpView;
    }

    /**
     * Keeps track of current help page number
     *
     * @return currentPageIndex
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    /**
     * Calls help window
     *
     * @return helpPageContainer
     */
    public SubScene getHelpPageContainer() {
        return helpPageContainer;
    }
}