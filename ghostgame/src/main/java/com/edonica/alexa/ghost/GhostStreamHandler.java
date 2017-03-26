package com.edonica.alexa.ghost;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.Arrays;
import java.util.HashSet;

public class GhostStreamHandler extends SpeechletRequestStreamHandler {

    public GhostStreamHandler() {
        super(new GhostSpeechlet(), GetSupportedApplicationIds());
    }

    static HashSet<String> GetSupportedApplicationIds() {
        String[] supportedApplicationIds = {
                "amzn1.ask.skill.1743b9db-02f5-424b-ae86-746ad7bdde6a"
        };
        return new HashSet<>(Arrays.asList(supportedApplicationIds));
    }
}
