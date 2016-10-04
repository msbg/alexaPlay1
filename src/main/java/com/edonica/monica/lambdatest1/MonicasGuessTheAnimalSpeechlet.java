package com.edonica.monica.lambdatest1;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class MonicasGuessTheAnimalSpeechlet implements Speechlet {

    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        System.out.println("received : " + request.toString() + ":" + session.toString());
    }

    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {

        System.out.println("received : " + request.toString() + ":" + session.toString());
        String speechText = "Welcome to the Alexa Skills Kit, you can say hello";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        System.out.println("received : " + request.toString() + ":" + session.toString());
        String speechText = "Welcome to the Alexa Skills Kit, you can say hello";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        System.out.println("received : " + request.toString() + ":" + session.toString());
    }
}
