package com.edonica.monica.lambdatest1;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by me on 02/10/2016.
 */
public class MonicasGuessTheAnimalSpeechletHandler extends SpeechletRequestStreamHandler {
    public MonicasGuessTheAnimalSpeechletHandler() {
        super(new MonicasGuessTheAnimalSpeechlet(), new HashSet<String>(Arrays.asList(new String[]{"amzn1.ask.skill.9ac905b0-c0f9-41f2-9a62-54469f022e58"})));
    }
}