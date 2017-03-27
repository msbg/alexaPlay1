package com.edonica.alexa.ghost.model;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Words {

    public boolean useRandom = true;

    private int getRandom(int bounds) {
        if(useRandom) {
            return new Random().nextInt(bounds);
        } else {
            //for use during testing
            return 0;
        }
    }
    List<String> itemList = new ArrayList<>();
    public Words() {
        //load words from resource
        String words = null;
        try {
            words = IOUtils.toString(this.getClass().getResourceAsStream("/5000FrequencyList.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String rows[] = words.split("\n");
        for( String row : rows ) {
            String items[] = row.split(",");
            itemList.add(items[1].toLowerCase());
        }
        Collections.sort(itemList);
    }
    
    public String getPossibleWord(String stringSoFar) {
        int startIndex = 0;
        while(startIndex< itemList.size() && !itemList.get(startIndex).startsWith(stringSoFar)) {
            startIndex++;
        }
        int endIndex = startIndex;
        while(endIndex < itemList.size() && itemList.get(endIndex).startsWith(stringSoFar) ) {
            endIndex++;
        }
        
        List<String> possibleWords = itemList.subList(startIndex, endIndex);
        if(possibleWords.size()==0) {
            return "";
        } else {
            String word = possibleWords.get(getRandom(possibleWords.size()));
            return word;
        }
    }

    public boolean isWord(String wordSoFar) {
        return itemList.contains(wordSoFar);
    }

    public Character getNextPossibleLetter(String wordSoFar) {
        String word = getPossibleWord(wordSoFar);
        if(word.length()>wordSoFar.length()) {
            String nextLetter = word.substring(wordSoFar.length());
            return nextLetter.toCharArray()[0];
        }
        return null;
    }
}
