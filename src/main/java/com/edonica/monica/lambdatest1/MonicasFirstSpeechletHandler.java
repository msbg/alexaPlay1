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
        super(new MonicasFirstSpeechlet(), new HashSet<String>(Arrays.asList(new String[]{"amzn1.ask.skill.1743b9db-02f5-424b-ae86-746ad7bdde6a"})));
    }
}
