package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class IsItFail extends AbstractTransition {
    public IsItFail() {
        super(GameState.IsItA, GameState.WhatIsIt);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNo);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return SpeechHelpers.makeFullFatResponse("What was it?  Add an, or a, to help me understand.");
    }
}
