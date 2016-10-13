package com.edonica.decision.tree;

import com.amazon.speech.speechlet.*;
import com.edonica.decision.tree.model.*;
import com.edonica.decision.tree.states.StateBase;
import com.edonica.decision.tree.states.StateRegistry;

public class DecisionTreeSpeechlet implements Speechlet {

    public DecisionTreeSpeechlet() {
        log("DecisionTreeSpeechlet created");
    }

    void log(String s) {
        System.out.println("Edonica " + s );
    }

    public void onSessionStarted(SessionStartedRequest intentRequest, Session session) throws SpeechletException {
        log("Session Start User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());
        session.setAttribute(SessionKey.GameState.toString(), GameState.Welcome.toString());
    }

    public SpeechletResponse onLaunch(LaunchRequest intentRequest, Session session) throws SpeechletException {
        log("Launch with User:" + session.getUser().getUserId() + " Session:" + session.getSessionId());
        String speechText = "Welcome to Thing Guesser.  Say New Game to start";
        return SpeechHelpers.makeFullFatResponse(speechText);
    }

    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {

        RequestContext context = new RequestContext(this, intentRequest, session);
        context.DumpRequest();

        StateRegistry stateRegistry = new StateRegistry();

        GameState fromState = context.getGameState();
        StateBase stateOld = stateRegistry.getState(fromState);

        String actionText = stateOld.handleRequest(context);

        //Get the new state
        StateBase stateNew = stateRegistry.getState(context.getGameState());

        //And get any new text
        String newStateText = stateNew.getText(context);

        String responseText;

        if( actionText == null ) {
            responseText = newStateText;
        } else {
            responseText = actionText + "  " + newStateText;
        }

        return SpeechHelpers.makeFullFatResponse(responseText);
    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        System.out.println("Edonica Session End : " + request.toString() + ":" + session.toString());
    }


}
