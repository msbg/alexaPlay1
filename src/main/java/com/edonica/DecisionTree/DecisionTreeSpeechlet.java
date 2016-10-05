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

        AddStateToMap(stateWelcome);
        AddStateToMap(stateNewObj);
    }

    void Log(String s) {
        System.out.println("Edonica " + s );
    }

    public void onSessionStarted(SessionStartedRequest intentRequest, Session session) throws SpeechletException {
        Log("Session Start User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());
    }

    public SpeechletResponse onLaunch(LaunchRequest intentRequest, Session session) throws SpeechletException {
        Log("Launch with User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());

        String speechText = "Welcome to Decision Tree.  Say New Game to start";
        session.setAttribute(StateGeneric.AttributeKeys.State, stateWelcome.getStateID());

        return stateWelcome.MakeFullFatResponse(speechText);
    }

    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {

        Request request = new Request(this, intentRequest, session);
        request.DumpRequest();

        String stateName = request.getSessionString(StateGeneric.AttributeKeys.State);

        Log("Handling request in state " + stateName);

        StateGeneric selectedIntent = States.getOrDefault(stateName, stateWelcome);

        return selectedIntent.HandleRequest(request);

    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        System.out.println("Edonica Session End : " + request.toString() + ":" + session.toString());
    }

    private void AddStateToMap(StateGeneric state) {
        States.put(state.getStateID(), state);
    }

    public StateWelcome stateWelcome = new StateWelcome();
    public StateNewObj stateNewObj = new StateNewObj();
    private StateUnknown stateUnknown = new StateUnknown();
    private Map<String,StateGeneric> States = new HashMap<String,StateGeneric>();

}
