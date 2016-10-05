package com.edonica.DecisionTree;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;

import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import java.util.*;

public class DecisionTreeSpeechlet implements Speechlet {

    public DecisionTreeSpeechlet() {
        Log("DecisionTreeSpeechlet created");
    }

    void Log(String s) {
        System.out.println("Edonica " + s );
    }

    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        Log("Session Start User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());
    }

    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        Log("Launch with User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());

        String speechText = "Welcome to Decision Tree";

        // Create the Simple card content.
        return MakeFullFatResponse(speechText);
    }


    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        Intent intent = request.getIntent();
        DumpRequest(request, session);

        String intentName = intent.getName();

        if(intentName.equals("IntentStop")) {
            Log("Saying goodbyte");

            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Goodbye");
            return SpeechletResponse.newTellResponse(speech);
        }
        else if(intentName.equals("IntentSetNumber")) {
            String number = intent.getSlot("number").getValue();
            session.setAttribute("Number", number);
            // Create the Simple card content.
            return MakeFullFatResponse("Setting number to " + number);
        }
        else if(intentName.equals("IntentGetNumber")) {
            Object number = session.getAttribute("Number");
            // Create the Simple card content.
            return MakeFullFatResponse("Your session number is " + number);
        }
        else if(intentName.equals("IntentFreeText")) {
            String speechText = GetStringFromComponents(intent);
            // Create the Simple card content.
            return MakeFullFatResponse(speechText);
        }
        else {
            return MakeFullFatResponse("Unrecognised intent " + intentName);
        }
    }

    private SpeechletResponse MakeFullFatResponse(String speechText) {
        SimpleCard card = new SimpleCard();
        card.setTitle("Decision Tree");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private void DumpRequest(IntentRequest intentRequest, Session session) {
        Intent intent = intentRequest.getIntent();

        Log("OnIntent Intent: " + intent.getName() + " User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());

        for( Map.Entry<String, Slot> slotEntry : intent.getSlots().entrySet()) {
            Slot slot = slotEntry.getValue();
            Log("  Slot " + slotEntry.getKey() + " (" + slot.getName() + ") = " + slot.getValue() );
        }

        for( Map.Entry<String, Object> attributeEntry : session.getAttributes().entrySet()) {
            Log("  Session " + attributeEntry.getKey() + " = " + attributeEntry.getValue() );
        }
    }

    private String GetStringFromComponents(Intent intent) {
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

        return responseBuilder.toString();
    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        System.out.println("Edonica Session End : " + request.toString() + ":" + session.toString());
    }
}
