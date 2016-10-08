package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class WhatQuestionConfirmNo extends AbstractTransition {
    public WhatQuestionConfirmNo() {
        super(GameState.WhatQuestionConfirm, GameState.WhatQuestion);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNo);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return SpeechHelpers.makeFullFatResponse("OK, let's try again.  What was the question?");
    }
}
