/*
   * @(#) Navigation.java 1.1 2018/02/12
   *
   * Copyright (c) 2018 University of Wales, Aberystwyth.
   * All rights reserved.
   *
   */
package uk.ac.aber.cs221.GP01.main.java.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import uk.ac.aber.cs221.GP01.main.java.ui.controllers.*;

import java.util.HashMap;

/**
 * Navigation - Control the screens being displayed
 * <p>
 * All screens are stored in a HashMap and can be activated, added or removed. The screen are stored as an FXML Loader with
 * an FXML file and root pane 'pre-loaded'. Overlays and Scenes are displayed differently and are both handled in this class
 *
 * @author Rhys Evans (rhe24@aber.ac.uk)
 * @author Nathan Williams (naw21)
 * @version 1.1
 * @see INavigation
 */

public class Navigation implements INavigation {

    /**
     * The singleton instance of the Navigation class
     */
    private static INavigation navController;

    /**
     * Default constructor for the Navigation
     */
    private Navigation(){}
    
    /**
     * All screens to be stored, store Type as Key and an FXML loader as value
     */
    private HashMap<ScreenType, FXMLLoader> screens = new HashMap<>();

    /**
     * The main scene of the program
     */
    private Scene main;

    /**
     * Add screen to the hashmap
     *
     * @param name - The name of the screen to be added
     * @param loader = the FXML loader of the screen to be added
     */
    public void add(ScreenType name, FXMLLoader loader){
        screens.put(name, loader);
    }

    /**
     * Remove  a Screen from the HashMap
     *
     * @param name - The screen to be removed
     */
    public void remove(ScreenType name){
        screens.remove(name);
    }

    /**
     * Switch to a given screen
     *
     * @param newScreen - The screen to switch to
     */
    public void switchScreen(ScreenType newScreen){
        // Only allow screen switching if screen type isn't an overlay
        if(screens.get(newScreen).getController() instanceof BaseScreen){
            //checks if the View needs prep before displaying
            if(screens.get(newScreen).getController() instanceof INeedPrep) {
                ((INeedPrep) screens.get(newScreen).getController()).prepView();
            }
            main.setRoot(screens.get(newScreen).getRoot());
        }
    }

    /**
     * Show a given overlay
     *
     * @param overlay - The overlay to be shown
     * @param parent - The parent scene of the overlay
     */
    public void showOverlay(ScreenType overlay, BaseScreen parent){
        // Hide all overlays before showing any more
        hideAllOverlays(parent);

        // Verify that the overlay is of type BaseOverlay
        if(screens.get(overlay).getController() instanceof BaseOverlay){
            // Disable the background of the parent screen
            parent.getMainNode().setDisable(true);

            // If the parent screen is a GameView, then disable the game cube
            if(parent instanceof GameView){
                ((IGame) parent).getCubeContainer().setVisible(false);
            }
            
            // Set the parent of the overlay
            ((BaseOverlay) screens.get(overlay).getController()).setParentController(parent);

            //checks if the overlay needs prep before displaying
            if(screens.get(overlay).getController() instanceof INeedPrep) {
                ((INeedPrep) screens.get(overlay).getController()).prepView();
            }

            // Add the overlay's FXML to the parent screen's root
            parent.getRoot().getChildren().add(screens.get(overlay).getRoot());
        }
    }

    /**
     * Hide a given overlay
     *
     * @param overlay - The overlay to be hidden
     */
    public void hideOverlay(ScreenType overlay, BaseScreen parent){

        // Verify that the overlay is of type BaseOverlay
        if(screens.get(overlay).getController() instanceof BaseOverlay){

            // Remove the overlay's FXML from parent
            parent.getRoot().getChildren().remove(screens.get(overlay).getRoot());

            // Re-enable the parent's background
            parent.getMainNode().setDisable(false);

            // If the parent screen is Game Controller, re-enable the game cube
            if(parent instanceof GameView){
                ((IGame) parent).getCubeContainer().setVisible(true);
            }
        }
    }

    /**
     * Utility method to hide all overlays to prevent overlay overlapping
     */
    private void hideAllOverlays(BaseScreen parent) {
        // Remove all overlays from the parent
        parent.getRoot().getChildren().remove(parent.getRoot().lookup(".helpOverlay"));
        parent.getRoot().getChildren().remove(parent.getRoot().lookup(".endOverlay"));
        parent.getRoot().getChildren().remove(parent.getRoot().lookup(".settingsOverlay"));

        // Re-enable background
        parent.getMainNode().setDisable(false);
    }

    /**
     * Get the instantiated instance of the Navigation singleton
     *
     * @return navController - the Navigation object
     */
    public static INavigation getInstance(){
        if(navController == null){
            synchronized (UI.class){
                if(navController == null){
                    navController = new Navigation();
                }
            }
        }
        return navController;
    }

    /**
     * Get main scene
     *
     * @return main
     */
    public Scene getMain() {
        return main;
    }

    /**
     * Get screens
     *
     * @return screens
     */
    public HashMap<ScreenType, FXMLLoader> getScreens() {
        return screens;
    }

    /**
     * Set the main screen of the game
     *
     * @param main - the JavaFX scene object
     */
    public void setMainScene(Scene main){
        this.main = main;
    }
}
