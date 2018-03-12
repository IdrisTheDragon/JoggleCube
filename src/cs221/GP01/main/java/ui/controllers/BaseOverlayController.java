/*
 * @(#) BaseOverlayController.java 0.1 2018/02/23
 *
 * Copyright (c) 2018 University of Wales, Aberystwyth.
 * All rights reserved.
 *
 */

package cs221.GP01.main.java.ui.controllers;

/**
 * The Parent Class of any overlay controller
 * @author Rhys Evans (rhe24@aber.ac.uk)
 * @author Nathan Williams (naw21@aber.ac.uk)
 * @version 0.2
 */
public class BaseOverlayController {


    /**
     * An instance of the overlay's parent controller
     */
    protected BaseScreenController parentController;


    /**
     * Set the Overlay's parent controller
     * @param parent
     */
    public void setParentController(BaseScreenController parent){
        this.parentController = parent;
    }

}
