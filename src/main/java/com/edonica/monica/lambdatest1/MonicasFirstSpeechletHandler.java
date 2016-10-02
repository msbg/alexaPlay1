package com.edonica.monica.lambdatest1;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by me on 02/10/2016.
 */
public class MonicasFirstSpeechletHandler extends SpeechletRequestStreamHandler {
    public MonicasFirstSpeechletHandler() {
        super(new MonicasFirstSpeechlet(), new HashSet<String>(Arrays.asList(new String[]{"amzn1.ask.skill.9ac905b0-c0f9-41f2-9a62-54469f022e58"})));
    }
}
