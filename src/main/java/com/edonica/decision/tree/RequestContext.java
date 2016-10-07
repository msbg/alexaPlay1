package com.edonica.decision.tree;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.edonica.decision.tree.transitions.DataNode;
import com.edonica.decision.tree.transitions.IntentName;
import com.edonica.decision.tree.transitions.WhatIsItQuestion;
import com.edonica.decision.tree.transitions.WhatIsItQuestionAnswer;

import java.util.*;


public class RequestContext {

    private DataNode dataNode;

    public RequestContext(DecisionTreeSpeechlet speechlet, IntentRequest request, Session session) {
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

    public void setSessionString(String key, String value) {
        session.setAttribute(key,value);
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

    public String getStringFromComponents() {
        Intent intent = request.getIntent();

        Set<String> keys = intent.getSlots().keySet();
        List<String> sortedKeys = new ArrayList<>(keys);
        sortedKeys.sort((o1, o2) -> o1.compareTo(o2));

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

    final private DecisionTreeSpeechlet speechlet;
    final private IntentRequest request;
    final private Session session;

    public DataNode getDataNode() {
        return dataNode;
    }

    public DataNode getChildNodeFromYesNoIntent() {
        String childId;
        if( isIntent(IntentName.IntentYes)) {
            childId = getDataNode().getYesId();
        } else if( isIntent(IntentName.IntentNo)) {
            childId = getDataNode().getNoId();
        } else {
            return null;
        }

        return DataNode.load(childId);
    }

    public void setDataNode(DataNode dataNode) {
        this.dataNode = dataNode;
    }

    public String getUserId() {
        return session.getUser().getUserId();
    }

    public void resetState() {
        //This could be more generic!
        session.removeAttribute(DataNode.class.getName());
        session.removeAttribute(WhatIsItQuestion.class.getName());
        session.removeAttribute(WhatIsItQuestionAnswer.class.getName());
    }
}