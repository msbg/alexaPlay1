package com.edonica.decision.tree;

import com.amazon.speech.speechlet.*;
import com.edonica.decision.tree.transitions.*;

import java.util.*;

public class DecisionTreeSpeechlet implements Speechlet {

    public DecisionTreeSpeechlet() {
        log("DecisionTreeSpeechlet created");

        addStateToMap(stateWelcome);
        addStateToMap(stateNewObj);
    }

    void log(String s) {
        System.out.println("Edonica " + s );
    }

    public void onSessionStarted(SessionStartedRequest intentRequest, Session session) throws SpeechletException {
        log("Session Start User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());
        session.setAttribute(GameState.class.getName(), GameState.Welcome.toString());
    }

    public SpeechletResponse onLaunch(LaunchRequest intentRequest, Session session) throws SpeechletException {
        log("Launch with User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());

        String speechText = "Welcome to Decision Tree.  Say New Game to start";

        return StateGeneric.makeFullFatResponse(speechText);
    }

    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {

        RequestContext request = new RequestContext(this, intentRequest, session);
        request.DumpRequest();
        GameState fromState = GameState.valueOf(request.getSessionString(GameState.class.getName()));
        DataNode dn = null;
        request.setDataNode(dn);
        IntentName intentName = IntentName.valueOf(request.getIntentName());
        TransitionRegistry transitionRegistry = new TransitionRegistry();
        AbstractTransition transition = transitionRegistry.getTransition(fromState,intentName, dn);
        log("Handling request in state " + fromState);

        //StateGeneric selectedIntent = states.getOrDefault(stateName, stateWelcome);

        return transition.handleRequest(request);

    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        System.out.println("Edonica Session End : " + request.toString() + ":" + session.toString());
    }

    private void addStateToMap(StateGeneric state) {
        states.put(state.getStateID(), state);
    }

    public StateWelcome stateWelcome = new StateWelcome();
    public StateNewObj stateNewObj = new StateNewObj();
    private StateUnknown stateUnknown = new StateUnknown();
    private Map<String,StateGeneric> states = new HashMap<String,StateGeneric>();

}
