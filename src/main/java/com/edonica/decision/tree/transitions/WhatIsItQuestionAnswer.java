package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class WhatIsItQuestionAnswer extends AbstractTransition {
    public WhatIsItQuestionAnswer() {
        super(GameState.WhatIsItQuestion, GameState.WhatIsItQuestionAnswer, IntentName.IntentFreeText);
    }

    @Override
    protected boolean isValidTransition(DataNode dn) {
        return true;
    }


    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return StateGeneric.makeFullFatResponse("And the answer for x would be?");
    }
}
