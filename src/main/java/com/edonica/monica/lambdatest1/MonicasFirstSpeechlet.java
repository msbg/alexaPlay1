package com.edonica.monica.lambdatest1;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;

import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MonicasFirstSpeechlet implements Speechlet {

    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        System.out.println("Edonica Session Start : " + request.toString() + ":" + session.toString());
    }

    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {

        System.out.println("Edonica Launch : " + request.toString() + ":" + session.toString());
        String speechText = "Edonica Launch Successful";

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
        System.out.println("Edonica onIntent: " + request.toString() + ":" + session.toString());
        Intent intent = request.getIntent();
        System.out.println("Intent name is " + intent.getName());

        String intentName = intent.getName();
        if(intentName.equals("StopIntent")) {
            System.out.println("Saying goodbyte");

            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Goodbye");
            return SpeechletResponse.newTellResponse(speech);
        }



        Set<String> keys = intent.getSlots().keySet();
        List<String> sortedKeys = new ArrayList<String>(keys);
        sortedKeys.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        StringBuilder responseBuilder = new StringBuilder();
        for( String key : sortedKeys) {
            String value = intent.getSlot(key).getValue();
            if( value != null ) {
                responseBuilder.append(" " + value);
            }
        }

        String speechText = responseBuilder.toString();


        for( Map.Entry<String, Slot> slotEntry : intent.getSlots().entrySet()) {
            Slot slot = slotEntry.getValue();
            System.out.println("  Slot " + slotEntry.getKey() + " (" + slot.getName() + ") = " + slot.getValue() );
        }

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
        System.out.println("Edonica Session Start : " + request.toString() + ":" + session.toString());
    }
}
