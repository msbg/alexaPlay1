package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.GameState;
import com.edonica.decision.tree.model.IntentName;
import com.edonica.decision.tree.model.RequestContext;

public class Transition {

    //Transition that only works for one intent
    Transition(IntentName intent, GameState to) {
        this.matchingIntents = new IntentName[]{intent};
        this.to = to;
    }

    //Match more than one intent
    Transition(IntentName[] matchingIntents, GameState to) {
        this.matchingIntents = matchingIntents;
        this.to = to;
    }

    boolean handlesIntent( IntentName intent) {
        for(IntentName handledIntent : matchingIntents) {
            if( intent == handledIntent) {
                return true;
            }
        }
        return false;
    }

    //Does this transition make sense?
    boolean isValidTransition(RequestContext context) {
        return true;
    }

    //What do you want to do during the transition
    void handleRequest(RequestContext request) {
    }

    boolean isReset() {
        return false;
    }

    //Text you want to prefix to the response from the new state
    String getTransitionPrefix(RequestContext request) {
        return null;
    }

    public IntentName[] getMatchingIntents() {
        return matchingIntents;
    }
    public GameState getTo() {
        return to;
    }

    final IntentName[] matchingIntents;
    final GameState to;

}
