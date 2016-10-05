package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class AnswerRestart extends AbstractTransition {
    public AnswerRestart() {
        super(GameState.WhatIsItQuestionAnswer, GameState.Welcome, IntentName.IntentFreeText);
    }

    @Override
    protected boolean isValidTransition(DataNode dn) {
        return true;
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        //String newItem = request.getStringFromComponents();
        //TODO Add the data node here (will be question, object and response)
        return StateGeneric.makeFullFatResponse("You added a new branch to the game");
    }
}
