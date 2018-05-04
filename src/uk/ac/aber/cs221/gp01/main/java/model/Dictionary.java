/*
   * @(#) Dictionary.java 1.1 2018/02/04
   *
   * Copyright (c) 2018 University of Wales, Aberystwyth.
   * All rights reserved.
   *
   */
package uk.ac.aber.cs221.gp01.main.java.model;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This Interface handles the backend dictionary, as well as searching, loading and retrieving the size of the
 * dictionary
 *
 * @author Samuel Jones - srj12
 * @version 1.1
 * @see IDictionary
 */
public class Dictionary implements IDictionary{
    private HashMap<String, String> dictionary = new HashMap();

    public Dictionary(){
        //Do nothing except make the hashmap instance variable
    }

    /**
     * Loads the dictionary into the local hash map handled by the object that is dictionary using the filename given
     *
     * @param filename Finds the file named as the String parameter
     */
    public void loadDictionary(String filename){
        long currentTime = System.currentTimeMillis();
        String input;
        InputStream file = getClass().getResourceAsStream("/uk/ac/aber/cs221/gp01/main/resource/dictionary/" + filename);
        Scanner in = new Scanner(file);
        while(in.hasNext()){
            input = in.nextLine().toUpperCase();
            dictionary.put(input, input);
        }
        System.out.print("Time taken to load dictionary: ");
        System.out.println(System.currentTimeMillis() - currentTime);
    }

    /**
     * Searches the dictionary utilising the has map for the word and returns true if the word is present false if not
     *
     * @param word the String that is being searched for in the dictionary
     * @return Returns true if the word is present, false if the word is not.
     */
    public boolean searchDictionary(String word){
        //Uses toUpperCase as all words are stored in upper case
        return dictionary.containsKey(word.toUpperCase());
    }

    /**
     * Returns the size of the local hash map
     *
     * @return An int equal to the size of the dictionary
     */
    public int getDictionarySize(){
        return dictionary.size();
    }
}
