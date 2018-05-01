/*
 * @(#) Settings.java 1.0 2018/03/12
 *
 * Copyright (c) 2018 University of Wales, Aberystwyth.
 * All rights reserved.
 *
 */

package cs221.GP01.main.java.ui;

import cs221.GP01.main.java.model.JoggleCube;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Settings - Class to handle the preferred game configuration
 * <p>
 * Handle the settings of the game and adapt them depending on user input in the settings overlay
 * @author Rhys Evans
 * @author Nathan Williams (naw21)
 * @version 1.1
 * @see ISettings
 * @see cs221.GP01.main.java.ui.controllers.Settings
 */
public class Settings implements ISettings{

    /**
     * The instance of the settings singleton class
     */
    private static ISettings settings;

    /**
     * Boolean to store whether or not colourblind is enabled
     */
    private boolean colorBlind = false;

    private static String languages[] = {"English","Cymraeg"};
    private static String currLang = "English";
    private static int timerLength = 180;


    /**
     * Constructor for settings class
     * todo: needed?
     */
    private Settings(){}

    public static String getCurrLang() {
        return currLang;
    }

    public static void setCurrLang(String currLang) {
        if(currLang.length() == 2){
            for(String lang : languages){
                if (lang.substring(0,2).toLowerCase().equals(currLang)) {
                    Settings.currLang = lang;
                    break;
                }
            }
        } else {
            Settings.currLang = currLang;
        }
        //when language is changed set the joggleCubeLanguage
        JoggleCube.getInstance().setLanguage();
    }

    public static String getCurrLangPrefix() {
        return currLang.substring(0,2).toLowerCase();
    }


    /**
     * Check if the colour blind option is enabled
     * @return true/false depending on state of the option
     */
    public boolean isColorBlindEnabled(){
        return colorBlind;
    }

    /**
     * Set the colour blind option to true/false
     * @param colorBlind
     */
    public void setColorBlindEnabled(boolean colorBlind){
        this.colorBlind = colorBlind;
    }


    /**
     * Clear the highscores and prompt user
     */
    public void clearHighScores(){

        // Create confirmation dialog and store result
        Dialog dialog = new Dialog();
        Optional<ButtonType> result = dialog.showConfirmationDialog("Clear High Scores", "Are you sure you want to clear all High Scores?");

        if(result.get() == ButtonType.OK){
            // Remove overall highscores
            //JoggleCube.getInstance().clearHighScores();
        }
    }



    /**
     * Returns an instance of the singleton class settings
     * @return settings
     */
    public static ISettings getInstance(){
        if(settings == null){
            synchronized (Settings.class){
                if(settings == null){
                    settings = new Settings();
                }
            }
        }
        return settings;
    }


    public static String[] getLanguages() {
        return languages;
    }

    public static int getTimerLength() {
        return timerLength;
    }

    public static void setTimerLength(int timerLength) {
        Settings.timerLength = timerLength;
    }
}
