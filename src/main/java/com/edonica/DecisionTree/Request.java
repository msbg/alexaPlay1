package com.edonica.DecisionTree;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;

import java.util.*;


public class Request {

    public Request(DecisionTreeSpeechlet speechlet, IntentRequest request, Session session) {
        this.speechlet = speechlet;
        this.request = request;
        this.session = session;
    }

    public String getIntentName() {
        return request.getIntent().getName();
    }

    public void DumpRequest() {
        Intent intent = request.getIntent();

        Log("OnIntent Intent: " + intent.getName() + " User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());

        for( Map.Entry<String, Slot> slotEntry : intent.getSlots().entrySet()) {
            Slot slot = slotEntry.getValue();
            Log("  Slot " + slotEntry.getKey() + " (" + slot.getName() + ") = " + slot.getValue() );
        }

        for( Map.Entry<String, Object> attributeEntry : session.getAttributes().entrySet()) {
            Log("  Session " + attributeEntry.getKey() + " = " + attributeEntry.getValue() );
        }
    }

    public String getSessionString(String key) {
        Object value = session.getAttribute(key);
        return value == null ? null : value.toString();
    }

    public void setState(String value) {
        session.setAttribute(StateGeneric.AttributeKeys.State, value);
    }

    public void setSessionString(String key, String value) {
        session.setAttribute(key,value);
    }

    public String getSlot(String key) {
        return request.getIntent().getSlot(key).getValue();
    }

    public DecisionTreeSpeechlet getSpeechlet() {
        return speechlet;
    }

    public String GetStringFromComponents() {
        Intent intent = request.getIntent();

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

    void Log(String s) {
        System.out.println("Edonica : " + s );
    }

    private DecisionTreeSpeechlet speechlet;
    private IntentRequest request;
    private Session session;

    public StateGeneric stateGeneric = new StateGeneric();
    public StateWelcome stateWelcome = new StateWelcome();

}
