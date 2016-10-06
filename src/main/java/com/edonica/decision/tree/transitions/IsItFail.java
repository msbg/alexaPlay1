package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class IsItFail extends AbstractTransition {
    public IsItFail() {
        super(GameState.IsItA, GameState.WhatIsIt, IntentName.IntentNo);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return true;
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return StateGeneric.makeFullFatResponse("What was it?");
    }
}
