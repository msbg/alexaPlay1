package com.edonica.alexa.ghost.model;


import com.amazon.speech.speechlet.IntentRequest;

public enum AlexaIntent {
    Session_start,
    Session_launch, 
    Yes, 
    No, 
    newLetter, 
    Challenge, 
    HumanChallengeReponse;

    public static AlexaIntent getIntentFromRequest(IntentRequest request) {
        return Session_start;
    }
}
