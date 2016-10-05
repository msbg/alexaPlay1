package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class WhatIsIt extends AbstractTransition {
    public WhatIsIt() {
        super(GameState.IsItA, GameState.WhatIsIt, IntentName.IntentFreeText);
    }

    @Override
    protected boolean isValidTransition(DataNode dn) {
        return true;
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return StateGeneric.makeFullFatResponse("What is it?");
    }
}
