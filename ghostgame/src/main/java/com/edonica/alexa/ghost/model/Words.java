package com.edonica.alexa.ghost.model;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Words {
    List<String> itemList = new ArrayList<>();
    public Words() {
        //load words from resource
        String words = null;
        try {
            words = IOUtils.toString(this.getClass().getResourceAsStream("5000FrequencyList.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String rows[] = words.split("\n");
        for( String row : rows ) {
            String items[] = row.split(",");
            itemList.add(items[1]);
        }
        Collections.sort(itemList);
    }
    
    public String getPossibleWord(String stringSoFar) {
        int startIndex = 0;
        while(!itemList.get(startIndex).startsWith(stringSoFar) && startIndex< itemList.size()) {
            startIndex++;
        }
        int endIndex = startIndex;
        while(itemList.get(endIndex).startsWith(stringSoFar) && endIndex < itemList.size()) {
            endIndex++;
        }
        
        List<String> possibleWords = itemList.subList(startIndex, endIndex);
        String word = possibleWords.get(new Random().nextInt(possibleWords.size()));
        
        return word;
    }

    public boolean isWord(String wordSoFar) {
        return itemList.contains(wordSoFar);
    }
}
