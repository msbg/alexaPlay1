package com.edonica.decision.tree.model;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.edonica.decision.tree.DecisionTreeSpeechlet;

import java.util.*;


public class RequestContext {

    public RequestContext(DecisionTreeSpeechlet speechlet, IntentRequest request, Session session) {
        this.speechlet = speechlet;
        this.request = request;
        this.session = session;
        this.endConversation = false;
    }

    public String getIntentName() {
        String name = request.getIntent().getName();
        //Substitute '.' for '_' to make it easier to use an enum
        return name.replace('.','_');
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

    public String getSessionString(SessionKey key) {
        Object value = session.getAttribute(key.toString());
        return value == null ? null : value.toString();
    }

    public void setSessionString(SessionKey key, String value) {
        session.setAttribute(key.toString(),value);
    }

    public String getSlot(String key) {
        return request.getIntent().getSlot(key).getValue();
    }

    public DecisionTreeSpeechlet getSpeechlet() {
        return speechlet;
    }

    public boolean isIntent(IntentName intent) {
        return IntentName.valueOf(getIntentName()) == intent;
    }

    public IntentName getIntent() {
        return IntentName.valueOf(getIntentName());
    }

    public String getStringFromComponents() {
        Intent intent = request.getIntent();

        Set<String> keys = intent.getSlots().keySet();
        List<String> sortedKeys = new ArrayList<>(keys);
        sortedKeys.sort(String::compareTo);

        StringBuilder responseBuilder = new StringBuilder();
        for( String key : sortedKeys) {
            String value = intent.getSlot(key).getValue();
            if( value != null ) {
                if( responseBuilder.length() > 0 ) {
                    responseBuilder.append(" ");
                }
                responseBuilder.append(value);
            }
        }

        return responseBuilder.toString();
    }

    void Log(String s) {
        System.out.println("Edonica : " + s );
    }


    public DataNode getDataNode() {
        return DataNode.fromContext(this);
    }

    public DataNode getChildNodeFromYesNoIntent() {
        String childId;
        if( isIntent(IntentName.AMAZON_YesIntent)) {
            childId = getDataNode().getYesId();
        } else if( isIntent(IntentName.AMAZON_NoIntent)) {
            childId = getDataNode().getNoId();
        } else {
            return null;
        }

        return DataNode.load(childId);
    }

    public String getUserId() {
        return session.getUser().getUserId();
    }

    public GameState getGameState() {
        return GameState.valueOf(getSessionString(SessionKey.GameState));
    }

    public void resetState() {
        //This could be more generic!
        session.removeAttribute(SessionKey.GameState.toString());
        session.removeAttribute(SessionKey.DataNode.toString());
        session.removeAttribute(SessionKey.ObjectName.toString());
        session.removeAttribute(SessionKey.UserQuestion.toString());
        session.setAttribute(SessionKey.GameState.toString(),GameState.Welcome.toString());
    }

    public boolean getEndConversation() {
        return endConversation;
    }
    public void setEndConversation(boolean end) {
        endConversation = end;
    }

    final private DecisionTreeSpeechlet speechlet;
    final private IntentRequest request;
    final private Session session;
    private boolean endConversation;

}
